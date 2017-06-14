package com.stackroute.gupshup.circleservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.stackroute.gupshup.circleservice.exception.CircleCreationException;
import com.stackroute.gupshup.circleservice.model.User;
import com.stackroute.gupshup.circleservice.service.UserService;


@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	 private UserService userService;
	//-----------------create user------------------
	 @RequestMapping(value="",method=RequestMethod.POST)
	    public ResponseEntity<User> saveUser(@RequestBody User user)
	    {
	    	User usersave = null;
	    	try{
	    		
	    	usersave=userService.createUser(user);
	    	
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
				users = userService.findAllUsers();
				if(users.isEmpty()){
		            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		            }
			} catch (CircleCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	    }
	    
	    //-------------------Retrieve Single User--------------------------------------------------------
	    
	    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<User> getUser(@PathVariable("id") String id) {
	        System.out.println("Fetching User with id " + id);
	        User user = null;
			try {
				user = userService.findById(id);
				 if (user == null) {
//			            System.out.println("User with id " + id + " not found");
			            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			        }
			} catch (CircleCreationException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
	       
	        return new ResponseEntity<User>(user, HttpStatus.OK);
	    }   
	    //---------update user-----------------------------
	    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User user) {
	       // System.out.println("Updating User " + id);
	    	 User currentUser = null;
	    	try{
	       
	    		currentUser = userService.findById(id);
	        if (currentUser==null) {
	            System.out.println("User with id " + id + " not found");
	            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	        }

	        currentUser.setPersonName(user.getPersonName());
	        
	        currentUser.setMobile(user.getMobile());
	        userService.updateUser(currentUser);
	        
	        
	        }
	        catch (CircleCreationException e) {
				// TODO Auto-generated catch block
				e.getMessage();
	        }
	        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	    }
	//-----------------Delete user by id------------------
	 @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	    public ResponseEntity deleteUser(@PathVariable String id)
	    {
		 Map msgMap = new HashMap<String,String>();
	        msgMap.put("message","User deleted successsfully");
	    	try {
				userService.deleteUser(id);
				
		        } catch (CircleCreationException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
	    	 return new ResponseEntity<Map<String,String>>(msgMap, HttpStatus.OK);

	    }
	 //-----------------Delete all user------------------
	 @RequestMapping(value = "/deleteall", method = RequestMethod.DELETE)
	    public ResponseEntity<User> deleteAllUsers() {
	        try{
	        userService.deleteAllUsers();
	        }
	        catch (CircleCreationException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
	        return new ResponseEntity<User>(HttpStatus.OK);
	    }
	 	
	 

}
