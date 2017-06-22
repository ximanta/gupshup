package com.stackroute.gupshup.recommendationservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.gupshup.recommendationservice.entity.User;
import com.stackroute.gupshup.recommendationservice.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public Map<String, Object> createUser(){
		return userRepository.getTrios("ay21","ayu","agr","female","books",18071994);
		//System.out.println(userRepository.findAll().toString());
	}
	
	public Iterable<Map<String, Object>> createRelation(){
		return userRepository.getRelation("ayu18", "ay21");
	}
	
}
