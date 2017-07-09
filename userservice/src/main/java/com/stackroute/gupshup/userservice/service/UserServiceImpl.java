package com.stackroute.gupshup.userservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.userservice.domain.Activity;
import com.stackroute.gupshup.userservice.domain.Create;
import com.stackroute.gupshup.userservice.domain.Delete;
import com.stackroute.gupshup.userservice.domain.Follow;
import com.stackroute.gupshup.userservice.domain.Person;
import com.stackroute.gupshup.userservice.domain.User;
import com.stackroute.gupshup.userservice.exception.UserNotFoundException;
import com.stackroute.gupshup.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private Environment environment;

	/* Registering a new user */
	@Override
	public User addUser(User user) {
		/* code to check if a user is already registered */
		User newUser = getUserByUserName(user.getUserName());
		if(newUser == null) {
			User savedUser = userRepository.save(user);
			/* creating the object of new registered user to publish it to mailbox service */
			Person person =new Person(null,"Person",savedUser.getUserName(),savedUser.getFirstName()+" "+savedUser.getLastName(),null);
			Activity activity = new Create(null,"Create","user registered",person,person);
			//			Group group =new Group(null,"595b872493515b0bfdce17e3","Group","gupshup");
			//			Join join =new Join(null,"Join",user.getUserName()+" has joined gupshup",person, group);
			/* publishing the created object to mailbox topic and recommendation topic */
			try {
				kafkaTemplate.send(environment.getProperty("userservice.topic.mailbox"),new ObjectMapper().writeValueAsString(activity));
				//				kafkaTemplate.send("circle",new ObjectMapper().writeValueAsString(join));
				kafkaTemplate.send(environment.getProperty("userservice.topic.recommendation"), new ObjectMapper().writeValueAsString(activity));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			user.setFollowing(new ArrayList<>());
			return savedUser;
		}
		return new User();
	}

	/* fetching a user by its user name */
	@Override
	public User getUserByUserName(String userName) {
		User user = userRepository.findOne(userName);
		if(user == null) {
			return null;
		}
		return user;
	}

	/* Update User profile details */
	@Override
	public String updateUser(User user) {
		/* code to find a user by his/her user name and then update his/her profile */
		String updateStatus = null;
		try {
			User updatedUser = getUserByUserName(user.getUserName());
			if(updatedUser == null) {
				throw new UserNotFoundException("user not found");
			}
			else {
				userRepository.save(user);
				updateStatus = "updated";
			}
		}
		catch(UserNotFoundException exception) {
			updateStatus = "NotUpdated";
		}
		return  updateStatus;
	}


	/* delete user */
	public String deleteUser(String userName)
	{
		/* code to find a user by his/her user name */
		User user1 = getUserByUserName(userName);;
		String deleteStatus = null;
		try {
			if(user1 == null) {
				throw new UserNotFoundException("user not found");
			}
			else {		
				/* deleting the user */
				userRepository.delete(userName);
				deleteStatus = "deleted";
			}
		} catch(UserNotFoundException exception) {
			deleteStatus = "NotDeleted";
		}
		return deleteStatus;
	}

	/* deleting a user profile */
	@Override
	public void deleteUserActivity(JsonNode node) {

		JsonNode sourceNode = node.path("actor");
		sourceNode.path("name").asText();

		JsonNode targetNode = node.path("object");
		String userName  = targetNode.path("id").asText();

		/* creating the object of user to delete */
		Person person = new Person(null, "Person", userName,null,null);
		Activity activity = new Delete(null, "DeleteUser", "user deleted", person, person);

		String deleteStatus = deleteUser(userName);
		if(deleteStatus.equalsIgnoreCase(deleteStatus)) {
			try {

				/* publishing the user object to mailbox topic and recommendation topic */
				kafkaTemplate.send(environment.getProperty("userservice.topic.mailbox"), new ObjectMapper().writeValueAsString(activity));
				kafkaTemplate.send(environment.getProperty("userservice.topic.recommendation"), new ObjectMapper().writeValueAsString(activity));
			}
			catch (JsonProcessingException ex) {
				ex.printStackTrace();
			}
		}
	}	

	/* method to check the type of activity  */

	@Override
	@KafkaListener(topics="user")
	public void checkActivityType(String activity)
	{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node;
		try {
			node = mapper.readTree(activity);

			String activityType = node.path("type").asText();
			if(activityType.equalsIgnoreCase("follow")) {
				followUser(node);
			}
			else if(activityType.equalsIgnoreCase("update")) {
				updateUserActivity(node);
			}
			else if(activityType.equalsIgnoreCase("delete")){
				deleteUserActivity(node);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* method to create following user list of top 10 following user */
	@Override
	public String followUser(JsonNode node) {
		/* fetching source user name from the node tree */
		JsonNode sourceNode = node.path("actor");
		String sourceUserName = sourceNode.path("id").asText();
		/* fetching target user name from the node tree */
		JsonNode targetNode = node.path("object");
		String targetUserName = targetNode.path("id").asText();

		User targetUser = getUserByUserName(targetUserName);
		User sourceUser = getUserByUserName(sourceUserName);
		/* updating the following list and following count of a user */
		List<User> followingList = sourceUser.getFollowing();
		if(followingList.contains(targetUser)) {
			return "already followed";
		} else {
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
			Person person1 =new Person(null,"Person",sourceUserName,null,null);
			Person person2 =new Person(null,"Person",targetUserName,null,null);

			Activity activity = new  Follow(null, "Follow", sourceUserName+" followed "+targetUserName, person1, person2);
			try {
				kafkaTemplate.send(environment.getProperty("userservice.topic.recommendation"), new ObjectMapper().writeValueAsString(activity));
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "successfully followed";
		}
	}

	/*  update user profile */
	public void updateUserActivity(JsonNode node)
	{
		try {
			kafkaTemplate.send(environment.getProperty("userservice.topic.mailboxrecommendation"), new ObjectMapper().writeValueAsString(node));
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		JsonNode sourceNode = node.path("actor");
		sourceNode.path("name");

		JsonNode updatedUser = node.path("object");

		User updatedUserObject = null;
		try {
			updatedUserObject = mapper.treeToValue(updatedUser, User.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		updateUser(updatedUserObject);
	}
}
