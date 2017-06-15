package com.stackroute.gupshup.circleservice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.stackroute.gupshup.circleservice.controller.hateoas.LinkAssembler;
import com.stackroute.gupshup.circleservice.exception.CircleCreationException;
import com.stackroute.gupshup.circleservice.model.Circle;
import com.stackroute.gupshup.circleservice.model.User;
import com.stackroute.gupshup.circleservice.service.UserService;


@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	 private UserService userService;
	 @Autowired
	 private LinkAssembler linkAssembler;
	 
	//-----------------create user------------------
	 @RequestMapping(value="",method=RequestMethod.POST)
	    public ResponseEntity<User> saveUser(@RequestBody User user)
	    {
	    	User usersave = null;
	    	try{
	    		if(userService.createUser(user)!=null)
	    		{
	    			usersave=userService.createUser(user);
	    		}
	    		else
	    		{
	    			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	    		}
	    	
	    	}
	    	 catch(CircleCreationException circleCreationException){
	    		 circleCreationException.getMessage();
	    	 }
	    	 
	    	return new ResponseEntity<User>(usersave, HttpStatus.CREATED);
	        
	    }
	
	//-------------------Retrieve All Users--------------------------------------------------------
	    
	    @RequestMapping(value = "", method = RequestMethod.GET)
	    public ResponseEntity<List<User>> listAllUsers() {
	        List<User> users = null;
			try {
				
				if(userService.findAllUsers().isEmpty()){
		            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		         }
				else
				{
					users = userService.findAllUsers();
					linkAssembler.assembleLinksForUserList(users);
				}
			} catch (CircleCreationException e) {
				return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
			}
	        
	        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	    }
	    
	    //-------------------Retrieve Single User--------------------------------------------------------
	    
	    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<User> getUser(@PathVariable("id") String id) {
	       
	        User user = null;
			try {
				
				 if (id != null) {
					 user = userService.findById(id);
				 }
				 else {
			            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			        }
			} 
			catch (CircleCreationException e) {
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
	       
	        return new ResponseEntity<User>(user, HttpStatus.OK);
	    }   
	    //---------update user-----------------------------
	    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User user){
	    	 User currentUser = null;
	    	try {
	        if (id==null) {
	            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	        }
	        else {
	        	currentUser = userService.findById(id);
	        	currentUser.setPersonName(user.getPersonName());
	        	currentUser.setEmail(user.getEmail());
	        	userService.updateUser(currentUser);
	        }
	       }
	        catch (CircleCreationException e) {
	        	 return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	    }
	//-----------------Delete user by id------------------
	 @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	    public ResponseEntity<User> deleteUser(@PathVariable String id) {
	    	try {
				if(id!=null) {
					userService.deleteUser(id);
				}
				else {
					return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
				}
				
		        } catch (CircleCreationException e) {
		        	return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
	    	 return new ResponseEntity<User>(HttpStatus.OK);

	    }
	 //-----------------Delete all user------------------
	 @RequestMapping(value = "/deleteall", method = RequestMethod.DELETE)
	    public ResponseEntity<User> deleteAllUsers() {
	        try{
	        	if(userService.findAllUsers()!=null) {
	        		userService.deleteAllUsers();
	        	}
	        	else {
	        		return  new ResponseEntity<User>(HttpStatus.NOT_FOUND); 
	        	}
	        }
	        catch (CircleCreationException e) {
				// TODO Auto-generated catch block
				return  new ResponseEntity<User>(HttpStatus.NOT_FOUND); 
			}
	        return new ResponseEntity<User>(HttpStatus.OK);
	    }
	 	
	 

}
