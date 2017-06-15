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
	public User createUser(User user) throws CircleCreationException {
		try{
			if(user!=null) {
				userRepo.save(user);
			}
			else {
				throw new CircleCreationException("User Detail More Needed");
			}
		}
		catch(CircleCreationException circlecreationException) {
			circlecreationException.getMessage();
		}
		return user; 
	}
	//---------find all user-------------------
	@Override
	public List<User> findAllUsers() throws CircleCreationException {
		try {
			if(userRepo.findAll()==null) {
				throw new CircleCreationException("No User Available");
			}
			
		}
		catch(CircleCreationException circlecreationException) {
			circlecreationException.getMessage();
		}
		return userRepo.findAll();
	}
	//---------find user by id------------
	@Override
	public User findById(String id) throws CircleCreationException {
		try {
			if(id==null) {
				throw new CircleCreationException("Cann't find User");
			}
			
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
		try {
			String userid = user.getId().toString();
			
			if(findById(userid).equals(user)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(CircleCreationException circlecreationException)	{
			circlecreationException.getMessage();
		}
		return true;
	}
	//----------update user by id---------------
	@Override
	public void updateUser(User currentUser) throws CircleCreationException {
		try {
			if(currentUser.getPersonName()==null) {
				throw new CircleCreationException("Cann't edit User");
			}
			else {
				userRepo.save(currentUser);
				
			}
		}
		catch(CircleCreationException circlecreationException) {
			circlecreationException.getMessage();
		}
		
	}
	//------delete user by id-------------------
	@Override
	public void deleteUser(String id) throws CircleCreationException {
		try {
			if(id==null) {
				throw new CircleCreationException("Cann't delete user");
			}
			else if(findById(id)==null) {
				throw new CircleCreationException("Id not found");
			}
			else {
				userRepo.delete(id);
			}			
		}
		catch(CircleCreationException circlecreationException)	{
			circlecreationException.getMessage();
		}		
	}
	//------------delete all users------------------------
	@Override
	public void deleteAllUsers() throws CircleCreationException {
		try{
			if(findAllUsers()==null) {
				throw new CircleCreationException("There is no users");
			}
			else {
				userRepo.deleteAll();
			}			
		}
		catch(CircleCreationException circlecreationException) {
			circlecreationException.getMessage();
		}
			
	}
}
