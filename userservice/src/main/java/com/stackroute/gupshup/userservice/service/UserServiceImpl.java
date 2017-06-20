package com.stackroute.gupshup.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.userservice.domain.Activity;
<<<<<<< HEAD
import com.stackroute.gupshup.userservice.domain.Delete;
import com.stackroute.gupshup.userservice.domain.Person;
import com.stackroute.gupshup.userservice.domain.User;
import com.stackroute.gupshup.userservice.exception.UserCreateException;
import com.stackroute.gupshup.userservice.exception.UserNotFoundException;
=======
import com.stackroute.gupshup.userservice.domain.Create;
import com.stackroute.gupshup.userservice.domain.Person;
import com.stackroute.gupshup.userservice.domain.User;
import com.stackroute.gupshup.userservice.exception.UserCreateException;
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
import com.stackroute.gupshup.userservice.producer.UserProducer;
import com.stackroute.gupshup.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

<<<<<<< HEAD
	UserRepository userRepository;
	UserProducer userProducer;
=======
	private UserRepository userRepository;
	private UserProducer userProducer;
	
	@Autowired
	public void setUserProducer(UserProducer userProducer) {
		this.userProducer = userProducer;
	}
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
<<<<<<< HEAD
	@Autowired
	public void setUserProducer(UserProducer userProducer) {
		this.userProducer = userProducer;
	}
	
	/* Add a User */
	@Override
	public User addUser(User user) {

		/* code to register user */
=======
	/* Registering a new user */
	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		/* code to check if a user is already registered */
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
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
<<<<<<< HEAD

	/* Find a user by username */
=======
	
	/* fetching a user by its user name */
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
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
			if(user1 == null)
			{
				throw new UserNotFoundException("User not registered");
			}
		}
		catch(UserNotFoundException exception)
		{
			return user1;
		}
		return user1;
	}

<<<<<<< HEAD
	/* Update User profile details */
	@Override
	public void updateUser(User user) {
		/* updating a user profile */
=======
	/* updating a user profile */
	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		/* code to update a user profile */
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
		userRepository.save(user);
	}
	

<<<<<<< HEAD
	/* Delete a user by its user name  */
	@Override
	public void deleteUser(String userName) {

		/* creating the object of user to publish it to the Mailbox service */
		User user = getUserByUserName(userName);
		Person person = new Person(null, "PERSON", user.getUserName());
		Activity activity = new Delete(null, "DELETE", "user deleted", person, person);
		
		/* deleting the user and publishing the user object to mailbox topic  */
		userRepository.delete((user.get_id()).toString());
		try {
			userProducer.publishUserActivity("Mailbox1", new ObjectMapper().writeValueAsString(person));
			
		}
		catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}	
=======
	/* deleting a user profile */
	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		/* code to delete a user account */
		userRepository.delete(userId);
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
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
<<<<<<< HEAD

		JsonNode sourceNode = node.path("actor");
		String sourceUserName = sourceNode.path("name").asText();
=======
		// TODO Auto-generated method stub
		/* fetching source user name from the node tree */
		JsonNode sourceNode = node.path("actor");
		String sourceUserName = sourceNode.path("name").asText();
		/* fetching target user name from the node tree */
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
		JsonNode targetNode = node.path("object");
		String targetUserName = targetNode.path("name").asText();
			
		User targetUser = getUserByUserName(targetUserName);
		User sourceUser = getUserByUserName(sourceUserName);

<<<<<<< HEAD

=======
		/* updating the following list and following count of a user */
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
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
<<<<<<< HEAD
		
	}
	
	/* update user profile */
=======
	}/* followUser() method end */

	/*  update user profile */
>>>>>>> 255148c75831a2151c343975f706ecf31bf62edf
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
