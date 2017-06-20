package com.stackroute.gupshup.userservice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.gupshup.userservice.domain.User;
import com.stackroute.gupshup.userservice.exception.UserNotFoundException;
import com.stackroute.gupshup.userservice.exception.UserCreateException;
import com.stackroute.gupshup.userservice.exception.UserDeleteException;
import com.stackroute.gupshup.userservice.exception.UserUpdateException;
import com.stackroute.gupshup.userservice.linkassembler.UserLinkAssembler;
import com.stackroute.gupshup.userservice.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="UserService", description="Operations pertaining to User")
@CrossOrigin()
@RestController
public class UserController {
	    
	    /* Autowire of UserService */
	    private UserService userService;
	    private UserLinkAssembler userLinkAssembler;
	    
	    @Autowired
	    public void setUserService(UserService userService)
	    {
	    	this.userService = userService;
	    }
	    
	    @Autowired
	    public void setUserLinkAssembler(UserLinkAssembler userLinkAssembler) {
	    	this.userLinkAssembler = userLinkAssembler;
	    }

	    /* Add a User */
	    @ApiOperation(value = "Add a User")
	    @RequestMapping(value="", method=RequestMethod.POST )
	    public ResponseEntity addUser(@Valid User validUser, BindingResult bindingResult, @RequestBody User user) throws UserCreateException {
	    	if(bindingResult.hasErrors()) {
	    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    	}
	    	Map<String, String> messageMap = new HashMap<>();
	    	try {
		    	User newUser = userService.addUser(user);
		    	
		    	if(newUser.get_id() == null) {
		    		throw new UserCreateException("User already registered");
		    	} else {
		    		User newUserLinks = userLinkAssembler.UserProfileLinks(newUser);
		    		return new ResponseEntity<User>(newUserLinks, HttpStatus.CREATED);
		    	}
		    } catch(UserCreateException exception) {
	    		return new ResponseEntity<>(exception.toString(), HttpStatus.NOT_FOUND);
	    	}
	    }
	    
	    /* GET User by username */
	    @ApiOperation(value = "Get user by userName",response = User.class)
	    @RequestMapping(value="/{userName}", method=RequestMethod.GET)
	    public ResponseEntity getUserByUserName(@PathVariable String userName) throws UserNotFoundException{
	    	try {
	    		if(userName == null) {
	    			throw new UserNotFoundException("user not found");
	    		}
	    		else {
	    			User user = userService.getUserByUserName(userName);
	    			if(user == null){
	    				throw new UserNotFoundException("User not registered");
	    			}
	    			else{
	    				User newUser = userLinkAssembler.followUserLinks(user);
		    			return new ResponseEntity<>(newUser, HttpStatus.OK);
	    			}
	    		}
	    	}
	    	catch(UserNotFoundException exception) {
	    		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	    	}
	    }
	    
	    /* Update User details */
	    @ApiOperation(value = "Update a User")
	    @RequestMapping(value="/{userId}",method=RequestMethod.PUT )
	    public ResponseEntity updateUser(@Valid User validUser, BindingResult bindingResult, @PathVariable String userId, @RequestBody User user) throws UserUpdateException{
	    	if(bindingResult.hasErrors()) {
	    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
	    	}
	    	try {
	    		if(userId == null) {
	    			throw new UserUpdateException("user could not be updated");
	    		}
	    		else {
	    			userService.updateUser(user);
	    			Map messageMap = new HashMap<String,String>();
	    			messageMap.put("message","User updated successfully");
	    	        return new ResponseEntity<Map<String,String>>(messageMap, HttpStatus.OK);
	    		}
	    	}
	    	catch(UserUpdateException exception) {
    	        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
	    	}
	    }

	    /* Delete a User */
	    @ApiOperation(value = "Delete a User")
	    @RequestMapping(value="/{userName}",method=RequestMethod.DELETE )
	    public ResponseEntity deleteUser(@PathVariable String userName) throws UserDeleteException{
		   try {
			   if(userName == null) {
				   throw new UserDeleteException("user could not be deleted");
			   }
			   else {
				   userService.deleteUser(userName);
				   Map messageMap = new HashMap<String,String>();
				   messageMap.put("message","User deleted successfully");
				   return new ResponseEntity<Map<String,String>>(messageMap, HttpStatus.OK);
			   }
	    	}
	    	catch(UserDeleteException exception) {
	    		return new ResponseEntity<>(exception.toString(), HttpStatus.NOT_FOUND);
	    	}
	    }
}
