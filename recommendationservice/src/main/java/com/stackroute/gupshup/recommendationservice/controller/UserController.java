package com.stackroute.gupshup.recommendationservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.gupshup.recommendationservice.entity.User;
import com.stackroute.gupshup.recommendationservice.service.CircleService;
import com.stackroute.gupshup.recommendationservice.service.UserService;

@RestController
@RequestMapping("demo")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CircleService circleService;
	
	/*@RequestMapping("/")
	public ResponseEntity<Map> createUser(){
		return new ResponseEntity<Map>(userService.createUser(), HttpStatus.OK);
		
	}
	*/
	/*@RequestMapping("/")
	public ResponseEntity<Map> createCircle(){
		return new ResponseEntity<Map>(circleService.createCircle(), HttpStatus.OK);
		
	}*/
	
	/*@RequestMapping("/")
	public ResponseEntity<Iterable<Map<String, Object>>> followUser(){
		
		return new ResponseEntity<Iterable<Map<String, Object>>>(userService.createRelation(), HttpStatus.OK);
	}*/
	
	/*@RequestMapping("/")
	public ResponseEntity<Iterable<Map<String, Object>>> createCreatedCircle(){
		
		return new ResponseEntity<Iterable<Map<String, Object>>>(circleService.createCreatedRelation(), HttpStatus.OK);
	}*/
	
	@RequestMapping("/")
	public ResponseEntity<Iterable<Map<String, Object>>> createSubscribedCircle(){
		
		return new ResponseEntity<Iterable<Map<String, Object>>>(circleService.createSubscribedRelation(), HttpStatus.OK);
	}


}
