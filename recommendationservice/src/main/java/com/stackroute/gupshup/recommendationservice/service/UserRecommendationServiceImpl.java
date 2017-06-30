package com.stackroute.gupshup.recommendationservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.recommendationservice.entity.UserRecommendation;
import com.stackroute.gupshup.recommendationservice.repository.UserRecommendationRepository;

@Service
public class UserRecommendationServiceImpl implements UserRecommendationService {
	
	@Autowired
	UserRecommendationRepository userRecommendationRepository;
	
	@Autowired
	UserRecommendationService userRecommendationService;
	
	@Override
	public Map<String, Object> createUser(UserRecommendation userRecommendation){
		
		System.out.println("Create Service:"+userRecommendation);
				return userRecommendationRepository.createUser(
				userRecommendation.getName(),
				userRecommendation.getFirstname(),
				userRecommendation.getLastname(),
				userRecommendation.getGender(),
				userRecommendation.getIntrest(),
				userRecommendation.getDOB());
	}
	
	@Override
	public String deleteUser(String user){
		userRecommendationRepository.deleteUserCreatedCircles(user);
		userRecommendationRepository.deleteUser(user);
		return "user deleted";
	}
	
	@Override
	public Map<String, Object> updateUser(UserRecommendation userRecommendation){
		System.out.println(userRecommendation);
		return userRecommendationRepository.updateUser(
				userRecommendation.getName(),
				userRecommendation.getFirstname(),
				userRecommendation.getLastname(),
				userRecommendation.getGender(),
				userRecommendation.getIntrest(),
				userRecommendation.getDOB());
	}
	
	@Override
	public Iterable<Map<String, Object>> follows(String user1, String user2){
		System.out.println("service "+user1+" "+user2);
		return userRecommendationRepository.follows(user1, user2);
	}
	
	@Override
	public Iterable<List<String>> followFriendOfFriend(String user){
		
		return userRecommendationRepository.followFriendOfFriend(user);
	}
	
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
		
			String name = actor.path("name").asText();
			String firstname = actor.path("firstname").asText();
			String lastname = actor.path("lastname").asText();
			String gender = actor.path("gender").asText();
			String intrest = actor.path("intrest").asText();
			String DOB = actor.path("DOB").asText();
		
			if(name==""||firstname==""||lastname==""||gender==""||intrest==""||DOB=="")
			{
				System.out.println("Create: Empty Fields");
			}
			else
			{
				System.out.println("create user");
				UserRecommendation userRecommendation = new UserRecommendation();
				int dob = Integer.parseInt(DOB);
				userRecommendation.setDOB(dob);
				userRecommendation.setFirstname(firstname);
				userRecommendation.setGender(gender);
				userRecommendation.setIntrest(intrest);
				userRecommendation.setLastname(lastname);
				userRecommendation.setName(name);
			
				userRecommendationService.createUser(userRecommendation);
			}
		}
		
		if(activityType.equalsIgnoreCase("Follow") && actorType.equalsIgnoreCase("Person"))
		{
		
			String user1 = actor.path("name").asText();
			String user2 = objectType.path("name").asText();
		
			if(user1==""||user2=="")
			{
				System.out.println("Follow: Name Field is empty");
			}
			else
			{
				userRecommendationService.follows(user1, user2);
			}
		}
		
		if(activityType.equalsIgnoreCase("Update") && actorType.equalsIgnoreCase("Person"))
		{
			String name = actor.path("name").asText();
			String firstname = actor.path("firstname").asText();
			String lastname = actor.path("lastname").asText();
			String gender = actor.path("gender").asText();
			String intrest = actor.path("intrest").asText();
			String DOB = actor.path("DOB").asText();
		
			if(name==""||firstname==""||lastname==""||gender==""||intrest==""||DOB=="")
			{
				System.out.println("Update: Empty Fields");
			}
			else
			{
				UserRecommendation userRecommendation = new UserRecommendation();
				int dob = Integer.parseInt(DOB);
				userRecommendation.setDOB(dob);
				userRecommendation.setFirstname(firstname);
				userRecommendation.setGender(gender);
				userRecommendation.setIntrest(intrest);
				userRecommendation.setLastname(lastname);
				userRecommendation.setName(name);
				
				userRecommendationService.updateUser(userRecommendation);
			}
		}
		
		if(activityType.equalsIgnoreCase("Delete") && actorType.equalsIgnoreCase("Person"))
		{
		
			String user = actor.path("name").asText();
		
			if(user=="")
			{
				System.out.println("Follow: Name Field is empty");
			}
			else
			{
				userRecommendationService.deleteUser(user);
			}
		}
		
	}
	
}
