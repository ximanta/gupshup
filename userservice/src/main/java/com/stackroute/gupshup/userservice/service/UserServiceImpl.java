package com.stackroute.gupshup.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		userRepository.save(user);
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		userRepository.delete(userId);
	}
	
	/*   check the type of activity  */
	@Override
	public void checkActivityType(JsonNode node)
	{
		String activityType = node.path("type").asText();
		if(activityType.equalsIgnoreCase("follow"))
		{
			followUser(node);
		}
		if(activityType.equalsIgnoreCase("update"))
		{
			updateUserActivity(node);
		}
	}
	
	

	@Override
	public void followUser(JsonNode node) {
		// TODO Auto-generated method stub

/*		String type = node.path("type").asText();
		if(type.equalsIgnoreCase("follow")) {*/
			JsonNode sourceNode = node.path("actor");
			String sourceUserName = sourceNode.path("name").asText();
			//System.out.println(sourceUserName);

			JsonNode targetNode = node.path("object");
			String targetUserName = targetNode.path("name").asText();
			//System.out.println(targetUserName);

			User targetUser = getUserByUserName(targetUserName);
			User sourceUser = getUserByUserName(sourceUserName);

			List<User> followingList = sourceUser.getFollowing();
			//System.out.println(followingList.size()+"here");
			
			if(sourceUser.getFollowingCount() < 10) {
				followingList.add(targetUser);
				/*for(int i=0;i<followingList.size();i++)
					System.out.println(followingList.get(i).getUserName());*/
				
				sourceUser.setFollowing(followingList);
				sourceUser.setFollowingCount(sourceUser.getFollowingCount()+1);
				userRepository.save(sourceUser);
				//System.out.println(sourceUser.getFollowingCount());
			} else {
				followingList.remove(0);
				followingList.add(targetUser);
				sourceUser.setFollowing(followingList);
				/*for(int i=0;i<followingList.size();i++)
					System.out.println(followingList.get(i).getUserName());*/

				sourceUser.setFollowingCount(sourceUser.getFollowingCount()+1);
				userRepository.save(sourceUser);
				//System.out.println(sourceUser.getFollowingCount());
			}
		//}
	}/*  followUser() method end  */

	
	
	/*  update the user when other user update his profile */
	public void updateUserActivity(JsonNode node)
	{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode sourceNode = node.path("actor");
		String sourceNodeName = sourceNode.path("type").asText();
		JsonNode sourceUserNode = node.path("object");
		
		User sourceUser=null;
		try {
			sourceUser = mapper.treeToValue(sourceUserNode, User.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		updateUser(sourceUser);
		
	}
	
}
