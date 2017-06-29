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
				return userRecommendationRepository.createUser(
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
		JsonNode objectType = node.path("object");
		String objType = objectType.path("type").asText();
		
		if(activityType.equalsIgnoreCase("Create") && actorType.equalsIgnoreCase("Person") && objType.equalsIgnoreCase("Person"))
		{
		
			String user = actor.path("name").asText();
		
		
			if(user=="")
			{
				System.out.println("Create: Name Field is empty");
			}
			else
			{
				System.out.println("create user");
				UserRecommendation userRecommendation = new UserRecommendation();
				userRecommendation.setDOB(0);
				userRecommendation.setFirstname("randeep");
				userRecommendation.setGender("female");
				userRecommendation.setIntrest("gallery");
				userRecommendation.setLastname("kaur");
				userRecommendation.setName(user);
			
				//userRecommendationService.createUser(userRecommendation);
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
		
		
	}
	
}
