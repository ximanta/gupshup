package com.stackroute.gupshup.recommendationservice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.gupshup.recommendationservice.entity.CircleRecommendation;
import com.stackroute.gupshup.recommendationservice.repository.CircleRecommendationRepository;

@Service
public class CircleRecommendationService {
	
	@Autowired
	CircleRecommendationRepository circleRecommendationRepository;
	
	public Map<String, Object> createCircle(CircleRecommendation circleRecommendation){
		
		return circleRecommendationRepository.createCircle(circleRecommendation.getKeyword(), circleRecommendation.getType());
	}
	
	public Iterable<Map<String, Object>> created(String user, String circle){
		
		System.out.println("service:"+user+" "+circle);
		return circleRecommendationRepository.created(user, circle);
	}
	
	public Iterable<Map<String, Object>> subscribed(String user, String circle){
		return circleRecommendationRepository.subscribed(user, circle);
	}

}
