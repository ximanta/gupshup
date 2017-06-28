package com.stackroute.gupshup.recommendationservice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.recommendationservice.entity.CircleRecommendation;
import com.stackroute.gupshup.recommendationservice.repository.CircleRecommendationRepository;

@Service
public class CircleRecommendationServiceImpl implements CircleRecommendationService {
	
	@Autowired
	CircleRecommendationRepository circleRecommendationRepository;
	
	@Override
	public Map<String, Object> createCircle(CircleRecommendation circleRecommendation){
		
		return circleRecommendationRepository.createCircle(circleRecommendation.getKeyword(), circleRecommendation.getType());
	}
	
	@Override
	public Iterable<Map<String, Object>> created(String user, String circle){
		
		System.out.println("service:"+user+" "+circle);
		return circleRecommendationRepository.created(user, circle);
	}
	
	@Override
	public Iterable<Map<String, Object>> subscribed(String user, String circle){
		return circleRecommendationRepository.subscribed(user, circle);
	}

	public void getActiviType(JsonNode node){
		
		CircleRecommendationService circleRecommendationService = null;
		
		System.out.println("entering activity");
		String activityType = node.path("type").asText();
		System.out.println("activity:"+activityType);
		
		if(activityType.equalsIgnoreCase("CreateCircle"))
		{
			System.out.println("entering create circle");
			JsonNode actor = node.path("actor");
			String user = actor.path("name").asText();
			JsonNode target = node.path("target");
			String keyword = target.path("circleName").asText();
			
			if(user==null||keyword==null)
			{
				System.out.println("name fields are either null or not specified correctly : MESSAGE CIRCLE");
			}
			
			else{
				
				CircleRecommendation circleRecommendation = null;
				circleRecommendation.setKeyword(keyword);
				circleRecommendation.setType(null);
				circleRecommendationService.createCircle(circleRecommendation);
				circleRecommendationService.created(user, keyword);
			}
		}
		
	}
}
