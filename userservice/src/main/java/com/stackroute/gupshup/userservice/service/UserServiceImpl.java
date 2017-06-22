package com.stackroute.gupshup.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.userservice.domain.Activity;

import com.stackroute.gupshup.userservice.domain.Create;
import com.stackroute.gupshup.userservice.domain.Delete;
import com.stackroute.gupshup.userservice.domain.Person;
import com.stackroute.gupshup.userservice.domain.User;
import com.stackroute.gupshup.userservice.exception.UserNotCreatedException;
import com.stackroute.gupshup.userservice.exception.UserNotFoundException;

import com.stackroute.gupshup.userservice.producer.UserProducer;
import com.stackroute.gupshup.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private UserProducer userProducer;
	
	@Autowired
	public void setUserProducer(UserProducer userProducer) {
		this.userProducer = userProducer;
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	

	/* Registering a new user */
	@Override
	public User addUser(User user) {

		/* code to check if a user is already registered */
		List<User> userList = userRepository.findAll();
		try {
			for(User newUser: userList) {
				if(newUser.getUserName().equalsIgnoreCase(user.getUserName())) {
					throw new UserNotCreatedException("already registered");
				}
			}
		} catch(UserNotCreatedException exception) {
			return new User();
		}
		/* creating the object of new registered user to publish it to mailbox service */
		Person person =new Person(null,"PERSON",user.getUserName());
		Activity activity = new Create(null,"CREATE","user registered",person,person);
		
		/* publishing the created object to mailbox topic and recommendation topic */
		try {
			userProducer.publishUserActivity("TestMailbox",new ObjectMapper().writeValueAsString(activity));
			userProducer.publishUserActivity("TestRecommendation", new ObjectMapper().writeValueAsString(activity));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return userRepository.save(user);
	}

	
	/* fetching a user by its user name */
	@Override
	public User getUserByUserName(String userName) {
		/* code to find a user by his/her user name */
		List<User> userList = userRepository.findAll();
		User user1 = null;
		try {
			for(User user: userList) {
				if(user.getUserName().equalsIgnoreCase(userName)) {
					user1 = user;
				}
			}
			if(user1 == null) {
				throw new UserNotFoundException("user not found");
			}
		} catch(UserNotFoundException exception) {
			return user1;
		}
		return  user1;
	}

	/* Update User profile details */
	@Override
	public String updateUser(User user) {
		
		/* code to find a user by his/her user name and then update his/her profile */
		List<User> userList = userRepository.findAll();
		User user1 = null;
		String updateStatus = null;
		try {
			for(User user2: userList) {
				if(user2.getUserName().equalsIgnoreCase(user.getUserName())) {
					user1 = user;
					userRepository.save(user1);
					updateStatus = "updated";
				}
			}
			if(user1 == null) {
				throw new UserNotFoundException("user not found");
			}
		} catch(UserNotFoundException exception) {
			updateStatus = "NotUpdated";
		}
		return  updateStatus;
	}

	/* deleting a user profile */
	@Override
	public String deleteUser(String userName) {
		/* code to find a user by his/her user name */
		List<User> userList = userRepository.findAll();
		User user1 = null;
		String deleteStatus = null;
		try {
			for(User user: userList) {
				if(user.getUserName().equalsIgnoreCase(userName)) {
					user1 = user;
				}
			}
			if(user1 == null) {
				throw new UserNotFoundException("user not found");
			}
			else {
				/* creating the object of user to publish it to the Mailbox service */
				Person person = new Person(null, "PERSON", user1.getUserName());
				Activity activity = new Delete(null, "DELETE", "user deleted", person, person);
				
				/* deleting the user */
				userRepository.delete((user1.get_id()).toString());
				
				/* publishing the user object to mailbox topic and recommendation topic */
				userProducer.publishUserActivity("TestMailbox", new ObjectMapper().writeValueAsString(activity));
				userProducer.publishUserActivity("TestRecommendation", new ObjectMapper().writeValueAsString(activity));
				deleteStatus = "deleted";
			}
		} catch(UserNotFoundException exception) {
			deleteStatus = "NotDeleted";
		}
		catch (JsonProcessingException ex) {
			ex.printStackTrace();
			deleteStatus = "NotDeleted";
		}
		return deleteStatus;
	}
	
	/* method to check the type of activity  */
	@Override
	public void checkActivityType(JsonNode node)
	{
		String activityType = node.path("type").asText();
		if(activityType.equalsIgnoreCase("follow")) {
			followUser(node);
		}
		else if(activityType.equalsIgnoreCase("update")) {
			updateUserActivity(node);
		}
	}
	
	/* method to create following user list of top 10 following user */
	@Override
	public void followUser(JsonNode node) {
		/* fetching source user name from the node tree */
		JsonNode sourceNode = node.path("actor");
		String sourceUserName = sourceNode.path("name").asText();
		
		/* fetching target user name from the node tree */
		JsonNode targetNode = node.path("object");
		String targetUserName = targetNode.path("name").asText();
			
		User targetUser = getUserByUserName(targetUserName);
		User sourceUser = getUserByUserName(sourceUserName);

		/* updating the following list and following count of a user */
		List<User> followingList = sourceUser.getFollowing();
		if(sourceUser.getFollowingCount() < 10) {
			followingList.add(targetUser);
			sourceUser.setFollowing(followingList);
			sourceUser.setFollowingCount(sourceUser.getFollowingCount()+1);
			userRepository.save(sourceUser);
		} else {
			followingList.remove(0);
			followingList.add(targetUser);
			sourceUser.setFollowing(followingList);
			sourceUser.setFollowingCount(sourceUser.getFollowingCount()+1);
			userRepository.save(sourceUser);
		}
	}

	/*  update user profile */
	public void updateUserActivity(JsonNode node)
	{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode sourceNode = node.path("actor");
		String sourceNodeName = sourceNode.path("type").asText();
		JsonNode sourceUserNode = node.path("object");
		
		User sourceUser = null;
		try {
			sourceUser = mapper.treeToValue(sourceUserNode, User.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		updateUser(sourceUser);
	}
}
