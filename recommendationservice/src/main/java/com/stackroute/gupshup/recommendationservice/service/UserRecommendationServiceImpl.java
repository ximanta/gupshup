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
	public void getActivityType(JsonNode node){
		System.out.println("entering activity");
		String activityType = node.path("type").asText();
		System.out.println("activity:"+activityType);
		
		if(activityType.equalsIgnoreCase("CreateUser"))
		{
		JsonNode actor = node.path("actor");
		String user = actor.path("name").asText();
		
		
		if(user=="")
		{
			System.out.println("Name Field is empty ");
		}
		else
		{
			UserRecommendation userRecommendation = new UserRecommendation();
			userRecommendation.setDOB(0);
			userRecommendation.setFirstname("charu");
			userRecommendation.setGender("female");
			userRecommendation.setIntrest("gallery");
			userRecommendation.setLastname("bhatt");
			userRecommendation.setName(user);
			
			userRecommendationService.createUser(userRecommendation);
		}
		}
	}
	
}
