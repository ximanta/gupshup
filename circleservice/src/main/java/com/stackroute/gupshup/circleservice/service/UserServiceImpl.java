package com.stackroute.gupshup.circleservice.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stackroute.gupshup.circleservice.exception.CircleCreationException;
import com.stackroute.gupshup.circleservice.model.User;
import com.stackroute.gupshup.circleservice.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepo;
	
	//-------create user-------------
	public User createUser(User user) throws CircleCreationException{
		try{
			userRepo.save(user);
			throw new CircleCreationException("");
		}
		catch(CircleCreationException circlecreationException)
		{
			circlecreationException.getMessage();
		}
		return user; 
	}
	//---------find all user-------------------
	@Override
	public List<User> findAllUsers() throws CircleCreationException {
		try{
			
			throw new CircleCreationException("");
		}
		catch(CircleCreationException circlecreationException)
		{
			circlecreationException.getMessage();
		}
		return userRepo.findAll();
	}
	//---------find user by id------------
	@Override
	public User findById(String id) throws CircleCreationException {
		try{
			
			throw new CircleCreationException("");
		}
		catch(CircleCreationException circlecreationException)
		{
			circlecreationException.getMessage();
		}
		return userRepo.findOne(id);
	}
	//---------is user exist---------------------
	@Override
	public boolean ifUserExist(User user) throws CircleCreationException {
		try{
			String userid = user.getId().toString();
			
			if(findById(userid).equals(user))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(CircleCreationException circlecreationException)
		{
			circlecreationException.getMessage();
		}
		return true;
	}
	//----------update user by id---------------
	@Override
	public void updateUser(User currentUser) throws CircleCreationException {
		
		try{
			userRepo.save(currentUser);
			throw new CircleCreationException("");
		}
		catch(CircleCreationException circlecreationException)
		{
			circlecreationException.getMessage();
		}
		
	}
	//------delete user by id-------------------
	@Override
	public void deleteUser(String id) throws CircleCreationException{
		try{
			userRepo.delete(id);
			throw new CircleCreationException("");
		}
		catch(CircleCreationException circlecreationException)
		{
			circlecreationException.getMessage();
		}
		
		
	}
	//------------delete all users------------------------
	@Override
	public void deleteAllUsers() throws CircleCreationException {
		try{
			userRepo.deleteAll();
			throw new CircleCreationException("");
		}
		catch(CircleCreationException circlecreationException)
		{
			circlecreationException.getMessage();
		}
		
		
	}
	
	
	


	


}
