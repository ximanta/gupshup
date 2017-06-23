package com.stackroute.gupshup.userservice.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
	    
	    /* Autowiring */
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
	    
	    @Autowired
		MessageSource messageSource;

	    /* Add a User */
	    @ApiOperation(value = "Add a User")
	    @RequestMapping(value="", method=RequestMethod.POST )
	    public ResponseEntity addUser(@Valid @RequestBody User user, BindingResult bindingResult) throws UserNotCreatedException {
	    	
	    	Locale locale = LocaleContextHolder.getLocale();
		    String message = messageSource.getMessage ("error.user.alreadyregistered", null, locale );
	    	
	    	if(bindingResult.hasErrors()) {
	    		//System.out.println(bindingResult.getAllErrors());
	    		String validationErrorMessage = messageSource.getMessage (bindingResult.getFieldError().getDefaultMessage(), null, locale );
	    		return new ResponseEntity<>(validationErrorMessage, HttpStatus.BAD_REQUEST);
	    	}
	    	try {
		    	User newUser = userService.addUser(user);
		    	
		    	if(newUser.get_id() == null) {
		    		throw new UserNotCreatedException("User already registered");
		    	} else {
		    		User newUserLinks = userLinkAssembler.UserProfileLinks(newUser);
		    		return new ResponseEntity<User>(newUserLinks, HttpStatus.CREATED);
		    	}
		    } catch(UserNotCreatedException exception) {
	    		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	    	} /*catch(NullPointerException exception) {
				return new ResponseEntity<>(exception.toString(), HttpStatus.NOT_FOUND);
	    	}*/
	    		//return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	    	
	    }
	    
	    /* GET User by username */
	    @ApiOperation(value = "Get user by userName",response = User.class)
	    @RequestMapping(value="/{userName}", method=RequestMethod.GET)
	    public ResponseEntity getUserByUserName(@PathVariable String userName) throws UserNotFoundException{
	    	
	    	Locale locale = LocaleContextHolder.getLocale();
		    String msg = messageSource.getMessage ("error.user.notregistered", null, locale );
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
	    		return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
	    	}
	    }
	    
	    /* Update User details */
	    @ApiOperation(value = "Update a User")
	    @RequestMapping(value="/{userName}",method=RequestMethod.PUT )
	    public ResponseEntity updateUser(@PathVariable String userName,@Valid @RequestBody User user, BindingResult bindingResult) throws UserNotUpdatedException{
	    	
	    	Locale locale = LocaleContextHolder.getLocale();
		    String message = messageSource.getMessage ("error.user.notupdated", null, locale );
	    	
	    	if(bindingResult.hasErrors()) {
	    		String validationErrorMessage = messageSource.getMessage (bindingResult.getFieldError().getDefaultMessage(), null, locale );
	    		return new ResponseEntity<>(validationErrorMessage, HttpStatus.BAD_REQUEST); 
	    	}
	    	try {
	    		if(userName == null) {
	    			throw new UserNotUpdatedException("user could not be updated");
	    		}
	    		else {
	    			if((userService.updateUser(user)).equalsIgnoreCase("updated")) {
	    				String successMessage = messageSource.getMessage ("error.user.notupdated", null, locale );
		    	        return new ResponseEntity<>(successMessage, HttpStatus.OK);
	    			}
	    			else {
	    				throw new UserNotUpdatedException("user could not be updated");
	    			}

	    		}
	    	}
	    	catch(UserNotUpdatedException exception) {
    	        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	    	}
	    }

	    /* Delete a User */
	    @ApiOperation(value = "Delete a User")
	    @RequestMapping(value="/{userName}",method=RequestMethod.DELETE )
	    public ResponseEntity deleteUser(@PathVariable String userName) throws UserNotDeletedException{
		   
	    	Locale locale = LocaleContextHolder.getLocale();
		    String message = messageSource.getMessage ("error.user.notdeleted", null, locale );
	    	
	    	try {
			   if(userName == null) {
				   throw new UserNotDeletedException("user could not be deleted");
			   }
			   else {
				   if((userService.deleteUser(userName)).equalsIgnoreCase("deleted")) {
					   String successMessage = messageSource.getMessage ("error.user.success.userdelete", null, locale );
					   return new ResponseEntity<>(successMessage, HttpStatus.OK);
				   }
				   else {
					   throw new UserNotDeletedException("user could not be deleted");
				   }
			   }
	    	}
	    	catch(UserNotDeletedException exception) {
	    		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	    	}
	    }
	    
}
