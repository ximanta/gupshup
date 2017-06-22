package com.stackroute.gupshup.userservice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.gupshup.userservice.domain.User;
import com.stackroute.gupshup.userservice.exception.UserNotCreatedException;
import com.stackroute.gupshup.userservice.exception.UserNotDeletedException;
import com.stackroute.gupshup.userservice.exception.UserNotFoundException;
import com.stackroute.gupshup.userservice.exception.UserNotUpdatedException;
import com.stackroute.gupshup.userservice.linkassembler.UserLinkAssembler;
import com.stackroute.gupshup.userservice.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@Api(value="UserService", description="Operations pertaining to User")
@RequestMapping("user")
public class UserController {
	    
	    /* Autowire of UserService */
	    private UserService userService;
	    private UserLinkAssembler userLinkAssembler;
	    
	    @Autowired
		MessageSource messageSource;
	    
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
<<<<<<< HEAD
	    public ResponseEntity addUser(@Valid User validUser, BindingResult bindingResult, @RequestBody User user) throws UserNotCreatedException {
	    	/*if(bindingResult.hasErrors()) {
	    		System.out.println(bindingResult.getAllErrors());
	    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    	}*/
=======
	    public ResponseEntity addUser(@Valid @RequestBody User user, BindingResult bindingResult) throws UserNotCreatedException {
	    	if(bindingResult.hasErrors()) {
	    		//System.out.println(bindingResult.getAllErrors());
	    		return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
	    	}
	    	//Map<String, String> messageMap = new HashMap<>();
>>>>>>> 204b0ae6c124ea16fd46c73f3f533270b0f63c12
	    	try {
		    	User newUser = userService.addUser(user);
		    	
		    	if(newUser.get_id() == null) {
		    		throw new UserNotCreatedException("User already registered");
		    	} else {
		    		User newUserLinks = userLinkAssembler.UserProfileLinks(newUser);
		    		return new ResponseEntity<User>(newUserLinks, HttpStatus.CREATED);
		    	}
		    } catch(UserNotCreatedException exception) {
<<<<<<< HEAD
	    		return new ResponseEntity<>(exception.toString(), HttpStatus.NOT_FOUND);
	    	} /*catch(NullPointerException exception) {
				return new ResponseEntity<>(exception.toString(), HttpStatus.NOT_FOUND);
	    	}*/
=======
	    		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	    	} 
>>>>>>> 204b0ae6c124ea16fd46c73f3f533270b0f63c12
	    }
	    
	    /* GET User by username */
	    @ApiOperation(value = "Get user by userName",response = User.class)
	    @RequestMapping(value="/{userName}", method=RequestMethod.GET)
	    public ResponseEntity getUserByUserName(@PathVariable String userName) throws UserNotFoundException{
	    	//Locale locale = LocaleContextHolder.getLocale();
			//String message = messageSource.getMessage ("prop:message:", null, locale );
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
	    @RequestMapping(value="/{userName}",method=RequestMethod.PUT )
<<<<<<< HEAD
	    public ResponseEntity updateUser(@Valid User validUser, BindingResult bindingResult, @PathVariable String userName, @RequestBody User user) throws UserNotUpdatedException{
	    	/*if(bindingResult.hasErrors()) {
	    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
	    	}*/
=======
	    public ResponseEntity updateUser(@PathVariable String userName,@Valid @RequestBody User user, BindingResult bindingResult) throws UserNotUpdatedException{
	    	if(bindingResult.hasErrors()) {
	    		return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST); 
	    	}
>>>>>>> 204b0ae6c124ea16fd46c73f3f533270b0f63c12
	    	try {
	    		if(userName == null) {
	    			throw new UserNotUpdatedException("user could not be updated");
	    		}
	    		else {
	    			if((userService.updateUser(user)).equalsIgnoreCase("updated")) {
		    			Map messageMap = new HashMap<String,String>();
		    			messageMap.put("message","User updated successfully");
		    	        return new ResponseEntity<Map<String,String>>(messageMap, HttpStatus.OK);
	    			}
	    			else {
	    				throw new UserNotUpdatedException("user could not be updated");
	    			}

	    		}
	    	}
	    	catch(UserNotUpdatedException exception) {
    	        return new ResponseEntity<>(exception.toString(), HttpStatus.NOT_FOUND);
	    	}
	    }

	    /* Delete a User */
	    @ApiOperation(value = "Delete a User")
	    @RequestMapping(value="/{userName}",method=RequestMethod.DELETE )
	    public ResponseEntity deleteUser(@PathVariable String userName) throws UserNotDeletedException{
		   try {
			   if(userName == null) {
				   throw new UserNotDeletedException("user could not be deleted");
			   }
			   else {
				   if((userService.deleteUser(userName)).equalsIgnoreCase("deleted")) {
					   Map messageMap = new HashMap<String,String>();
					   messageMap.put("message","User deleted successfully");
					   return new ResponseEntity<Map<String,String>>(messageMap, HttpStatus.OK);
				   }
				   else {
					   throw new UserNotDeletedException("user could not be deleted");
				   }
			   }
	    	}
	    	catch(UserNotDeletedException exception) {
	    		return new ResponseEntity<>(exception.toString(), HttpStatus.NOT_FOUND);
	    	}
	    }
}
