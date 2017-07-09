package com.stackroute.gupshup.userservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.userservice.domain.User;

public interface UserService {
	
	public User addUser(User user);
	public User getUserByUserName(String userName);
	public String updateUser(User user);
	public String deleteUser(String userName);
	public void checkActivityType(String activity);
	public String followUser(JsonNode node);
	public void updateUserActivity(JsonNode node);
	public void deleteUserActivity(JsonNode node);
}
