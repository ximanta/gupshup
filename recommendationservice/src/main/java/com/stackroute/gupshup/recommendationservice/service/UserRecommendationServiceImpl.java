package com.stackroute.gupshup.recommendationservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.recommendationservice.entity.UserRecommendation;
import com.stackroute.gupshup.recommendationservice.exception.RecommendationException;
import com.stackroute.gupshup.recommendationservice.repository.UserRecommendationRepository;

@Service
public class UserRecommendationServiceImpl implements UserRecommendationService {
	
	@Autowired
	UserRecommendationRepository userRecommendationRepository;
	
	@Autowired
	UserRecommendationService userRecommendationService;
	
	/*------method to create a user node in neo4j-------*/
	@Override
	public Map<String, Object> createUser(UserRecommendation userRecommendation) throws RecommendationException{
		String u = userRecommendationRepository.findByName(userRecommendation.getName());
		if(u!=null)
		{
			System.out.println(u);
			throw new RecommendationException("Username already exists");
		}
		else{
		System.out.println("Create Service:"+userRecommendation);
				return userRecommendationRepository.createUser(
				userRecommendation.getName(),
				userRecommendation.getFirstname(),
				userRecommendation.getLastname(),
				userRecommendation.getGender(),
				userRecommendation.getIntrest(),
				userRecommendation.getDOB());
		}
	}
	
	/*------method to delete a user node in neo4j-------*/
	@Override
	public String deleteUser(String user) throws RecommendationException{
		String u = userRecommendationRepository.findByName(user);
		if(u==null)
		{
			System.out.println("delete"+u);
			throw new RecommendationException("Username does not exist");
		}
		else{
		userRecommendationRepository.deleteUserCreatedCircles(user);
		userRecommendationRepository.deleteUser(user);
		return "user deleted";
		}
	}
	
	/*------method to update a user node in neo4j-------*/
	@Override
	public Map<String, Object> updateUser(UserRecommendation userRecommendation) throws RecommendationException{
		System.out.println(userRecommendation);
		String u = userRecommendationRepository.findByName(userRecommendation.getName());
		if(u==null)
		{
			System.out.println("update"+u);
			throw new RecommendationException("Username does not exist");
		}
		else{
		return userRecommendationRepository.updateUser(
				userRecommendation.getName(),
				userRecommendation.getFirstname(),
				userRecommendation.getLastname(),
				userRecommendation.getGender(),
				userRecommendation.getIntrest(),
				userRecommendation.getDOB());
		}
	}
	
	/*----------method to create a follow relationship in neo4j when user follows another user-------*/
	@Override
	public Iterable<Map<String, Object>> follows(String user1, String user2) throws RecommendationException{
		String u1 = userRecommendationRepository.findByName(user1);
		String u2 = userRecommendationRepository.findByName(user2);
		if(u1==null||u2==null)
		{
			System.out.println("follow:"+u1+" "+u2);
			throw new RecommendationException("Username does not exist");
		}
		else{
		System.out.println("service "+user1+" "+user2);
		return userRecommendationRepository.follows(user1, user2);
		}
	}
	
	/*--------recommendation method to follow friend of friend with distinct results-----*/
	@Override
	public List<Map<String,String>> followRecommendation(String user) throws RecommendationException{
		String u = userRecommendationRepository.findByName(user);
		if(u==null||user==null)
		{
			System.out.println("follow recomm:"+u);
			throw new RecommendationException("Username does not exist");
		}
		else
		{
			ArrayList<Map<String,String>> l1 = userRecommendationRepository.followSameCirclePeople(user);
			ArrayList<Map<String,String>> l2 = userRecommendationRepository.followPeople(user);
			ArrayList<Map<String,String>> l = new ArrayList<Map<String,String>>();
				l.addAll(l1);
				l.addAll(l2);
			return l;
		}
	}
	
	/*-------method to get propeties of the user through username from neo4j---------*/
	@Override
	public UserRecommendation findUser(String name){
		return userRecommendationRepository.findUser(name);
	}
	
	/*---------method to check activity type of activity stream when json is consumed through kafka-----------*/
	@Override
	public void getActivityType(JsonNode node)
	{
		System.out.println("user: entering activity");
		String activityType = node.path("type").asText();
		System.out.println("user: activity:"+activityType);
		JsonNode actor = node.path("actor");
		String actorType = actor.path("type").asText();
		System.out.println("user: actor: "+actorType);
		JsonNode objectType = node.path("object");
		//String objType = objectType.path("type").asText();
		//System.out.println("user: object: "+objType);
		
		if(activityType.equalsIgnoreCase("Create") && actorType.equalsIgnoreCase("Person"))
		{
		
			String name = actor.path("username").asText();
			String firstname = actor.path("firstname").asText();
			String lastname = actor.path("lastname").asText();
			String gender = actor.path("gender").asText();
			String i = actor.path("intrest").asText();
			List<String> intrest = new ArrayList<String>(Arrays.asList(i.split(",")));
			String DOB = actor.path("dob").asText();
		
			if(name==""||firstname==""||lastname==""||gender=="")
			{
				System.out.println("Create: Empty Fields");
			}
			else
			{
				System.out.println("create user");
				UserRecommendation userRecommendation = new UserRecommendation();
				userRecommendation.setDOB(DOB);
				userRecommendation.setFirstname(firstname);
				userRecommendation.setGender(gender);
				userRecommendation.setIntrest(intrest);
				userRecommendation.setLastname(lastname);
				userRecommendation.setName(name);
			
				try {
					userRecommendationService.createUser(userRecommendation);
				} catch (RecommendationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		if(activityType.equalsIgnoreCase("Follow") && actorType.equalsIgnoreCase("Person"))
		{
		
			String user1 = actor.path("id").asText();
			String user2 = objectType.path("id").asText();
		
			if(user1==""||user2=="")
			{
				System.out.println("Follow: Name Field is empty");
			}
			else
			{
				try {
					userRecommendationService.follows(user1, user2);
				} catch (RecommendationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(activityType.equalsIgnoreCase("Update") && actorType.equalsIgnoreCase("Person"))
		{
			String name = actor.path("username").asText();
			String firstname = actor.path("firstname").asText();
			String lastname = actor.path("lastname").asText();
			String gender = actor.path("gender").asText();
			String i = actor.path("intrest").asText();
			List<String> intrest = new ArrayList<String>(Arrays.asList(i.split(",")));
			String DOB = actor.path("dob").asText();
		
			if(name==""||firstname==""||lastname==""||gender==""||intrest==null||DOB=="")
			{
				System.out.println("Update: Empty Fields");
			}
			else
			{
				UserRecommendation userRecommendation = new UserRecommendation();
				userRecommendation.setDOB(DOB);
				userRecommendation.setFirstname(firstname);
				userRecommendation.setGender(gender);
				userRecommendation.setIntrest(intrest);
				userRecommendation.setLastname(lastname);
				userRecommendation.setName(name);
				
				try {
					userRecommendationService.updateUser(userRecommendation);
				} catch (RecommendationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(activityType.equalsIgnoreCase("Delete") && actorType.equalsIgnoreCase("Person"))
		{
		
			String user = actor.path("id").asText();
		
			if(user=="")
			{
				System.out.println("Follow: Name Field is empty");
			}
			else
			{
				try {
					userRecommendationService.deleteUser(user);
				} catch (RecommendationException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}

