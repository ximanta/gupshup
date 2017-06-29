package com.stackroute.gupshup.recommendationservice.service;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.recommendationservice.entity.CircleRecommendation;
import com.stackroute.gupshup.recommendationservice.entity.UserRecommendation;
import com.stackroute.gupshup.recommendationservice.repository.CircleRecommendationRepository;

@Service
public class CircleRecommendationServiceImpl implements CircleRecommendationService {
	
	@Autowired
	CircleRecommendationRepository circleRecommendationRepository;
	
	@Autowired
	CircleRecommendationService circleRecommendationService;
	
	@Override
	public Map<String, Object> createCircle(CircleRecommendation circleRecommendation){
		
		System.out.println("circle created");
		return circleRecommendationRepository.createCircle(circleRecommendation.getCircleId(), circleRecommendation.getKeyword(),circleRecommendation.getCircleName());
	}
	
	@Override
	public Iterable<Map<String, Object>> created(String user, String circleId){
		
		System.out.println("service:"+user+" "+circleId);
		System.out.println("created relationship");
		return circleRecommendationRepository.created(user, circleId);
	}
	
	@Override
	public Iterable<Map<String, Object>> subscribed(String user, String circleId){
		System.out.println("user subscribed");
		return circleRecommendationRepository.subscribed(user, circleId);
	}
	
	@Override
	public Iterable<List<String>> subscribeRecommendation(String user){
		
		return circleRecommendationRepository.subscribeRecommendation(user);
	}
	
	@Override
	public void getActiviType(JsonNode node){
		
		System.out.println("circle: entering activity");
		String activityType = node.path("type").asText();
		System.out.println("circle: activity:"+activityType);
		JsonNode actor = node.path("actor");
		String actorType = actor.path("type").asText();
		JsonNode objectType = node.path("object");
		String objType = objectType.path("type").asText();
		
		if(activityType.equalsIgnoreCase("Create") && actorType.equalsIgnoreCase("Person") && objType.equalsIgnoreCase("Group"))
		{
		
			String user = actor.path("name").asText();
			String circleId = objectType.path("name").asText();
		
			if(user=="" || circleId=="")
			{
				System.out.println("Create: userame or circlename Field is empty");
			}
			else
			{
				System.out.println("create circle");
				CircleRecommendation circleRecommendation = new CircleRecommendation();
				
				//circleRecommendation.setCircleId(circleId);
				circleRecommendation.setKeyword(circleId);
				circleRecommendation.setType("public");
				
				circleRecommendationService.createCircle(circleRecommendation);
				circleRecommendationService.created(user, circleId);
			}
		}
		
		if(activityType.equalsIgnoreCase("Join") && actorType.equalsIgnoreCase("Person") && objType.equalsIgnoreCase("Group"))
		{
		
			String user = actor.path("name").asText();
			String circleId = objectType.path("name").asText();
		
			if(user=="" || circleId=="")
			{
				System.out.println("Join: userame or circlename Field is empty");
			}
			else
			{
				circleRecommendationService.subscribed(user, circleId);
			}
		}
		
		
	}
}
