package com.stackroute.gupshup.userservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.userservice.domain.User;

public interface UserService {
	
	public User addUser(User user);
	public User getUserByUserName(String userName);
	public void updateUser(User user);
	public void deleteUser(String userId);
	public void followingOperations(JsonNode node);
}
