package com.stackroute.gupshup.authserver.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.gupshup.authserver.model.User;
import com.stackroute.gupshup.authserver.repository.UserRepository;

@RestController
public class AuthController {

	@Autowired
	UserRepository userRepository;
	
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@RequestMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user){
		String encryptPassword  = encoder.encode(user.getPassword());
		user.setPassword(encryptPassword);
		user.setRoles("USER");
		User savedUser = userRepository.save(user);
		return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
	}
	
	@RequestMapping("/me")
	public Principal login(Principal user){
		return user;
	}
}
