package com.stackroute.gupshup.circleservice.service;

import java.util.List;

import com.stackroute.gupshup.circleservice.exception.CircleCreationException;
import com.stackroute.gupshup.circleservice.model.User;

public interface UserService {
	public User createUser(User user) throws CircleCreationException;

	public List<User> findAllUsers() throws CircleCreationException;
	
	public User findById(String id) throws CircleCreationException;
	
	public void deleteUser(String id) throws CircleCreationException;

	public void deleteAllUsers() throws CircleCreationException;

	public void updateUser(User currentUser) throws CircleCreationException;

	public boolean ifUserExist(User user) throws CircleCreationException;

	

	
}
