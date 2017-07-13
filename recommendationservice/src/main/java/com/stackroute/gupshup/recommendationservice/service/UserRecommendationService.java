package com.stackroute.gupshup.recommendationservice.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.recommendationservice.entity.UserRecommendation;
import com.stackroute.gupshup.recommendationservice.exception.RecommendationException;

public interface UserRecommendationService {
	
	public Map<String, Object> createUser(UserRecommendation userRecommendation) throws RecommendationException;
	public String deleteUser(String user) throws RecommendationException;
	public Map<String, Object> updateUser(UserRecommendation userRecommendation) throws RecommendationException;
	public Iterable<Map<String, Object>> follows(String user1, String user2) throws RecommendationException;
	public Iterable<List<String>> followRecommendation(String user) throws RecommendationException;
	public UserRecommendation findUser(String name);
	public void getActivityType(JsonNode node);
}

