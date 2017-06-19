package com.stackroute.gupshup.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.userservice.domain.Activity;
import com.stackroute.gupshup.userservice.domain.Create;
import com.stackroute.gupshup.userservice.domain.Person;
import com.stackroute.gupshup.userservice.domain.User;
import com.stackroute.gupshup.userservice.exception.UserCreateException;
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
		// TODO Auto-generated method stub
		/* code to check if a user is already registered */
		List<User> userList = userRepository.findAll();
		try {
			for(User newUser: userList) {
				if(newUser.getUserName().equalsIgnoreCase(user.getUserName())) {
					throw new UserCreateException("already registered");
				}
			}
		} catch(UserCreateException exception) {
			return new User();
		}
		/* creating the object of new registered user to publish it to mailbox service */
		Person person =new Person(null,"PERSON",user.getUserName());
		Activity activity = new Create(null,"CREATE","user registered",person,person);
		
		/* publishing the created object to mailbox topic */
		try {
			userProducer.publishUserActivity("Mailbox1",new ObjectMapper().writeValueAsString(activity));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return userRepository.save(user);
	}
	
	/* fetching a user by its user name */
	@Override
	public User getUserByUserName(String userName) {
		// TODO Auto-generated method stub
		/* code to find a user by his/her user name */
		List<User> userList = userRepository.findAll();
		User user1 = null;
		for(User user: userList) {
			if(user.getUserName().equalsIgnoreCase(userName)) {
				user1 = user;
			}
		}
		return user1;
	}

	/* updating a user profile */
	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		/* code to update a user profile */
		userRepository.save(user);
	}

	/* deleting a user profile */
	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		/* code to delete a user account */
		userRepository.delete(userId);
	}
	
	/* check the type of activity  */
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
	
	/* method to create following list of top 10 following user */
	@Override
	public void followUser(JsonNode node) {
		// TODO Auto-generated method stub
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
	}/* followUser() method end */

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateUser(sourceUser);
	}
}
