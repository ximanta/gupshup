package com.stackroute.gupshup.recommendationservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.gupshup.recommendationservice.entity.UserRecommendation;
import com.stackroute.gupshup.recommendationservice.repository.UserRecommendationRepository;

@Service
public class UserRecommendationService {
	
	@Autowired
	UserRecommendationRepository userRecommendationRepository;
	
	public Map<String, Object> createUser(UserRecommendation userRecommendation){
				return userRecommendationRepository.createUser(
				userRecommendation.getName(),
				userRecommendation.getFirstname(),
				userRecommendation.getLastname(),
				userRecommendation.getGender(),
				userRecommendation.getIntrest(),
				userRecommendation.getDOB());
	}
	
	public Iterable<Map<String, Object>> follows(String user1, String user2){
		System.out.println("service "+user1+" "+user2);
		return userRecommendationRepository.follows(user1, user2);
	}
	
}
