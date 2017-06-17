package com.stackroute.gupshup.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.userservice.domain.User;
import com.stackroute.gupshup.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		/* code to register user */
		return userRepository.save(user);
	}

	@Override
	public User getUserByUserName(String userName) {
		// TODO Auto-generated method stub
		/* code to find a user by his/her user name */
		List<User> userList = userRepository.findAll();
		User user1 = null;
		for(User user: userList) {
			if(user.getUserName().equalsIgnoreCase(userName)) {
				user1 = userRepository.findOne(String.valueOf(user.get_id()));
			}
		}
		return user1;
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		/* updating a user profile */
		userRepository.save(user);
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		/* deleting a user account */
		userRepository.delete(userId);
	}

	@Override
	public void followingOperations(JsonNode node) {
		// TODO Auto-generated method stub
		/* code to create following list of top 10 following user */
		String type = node.path("type").asText();
		if(type.equalsIgnoreCase("follow")) {
			JsonNode sourceNode = node.path("actor");
			String sourceUserName = sourceNode.path("name").asText();
			
			JsonNode targetNode = node.path("object");
			String targetUserName = targetNode.path("name").asText();
			
			User targetUser = getUserByUserName(targetUserName);
			User sourceUser = getUserByUserName(sourceUserName);

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
	}
}
