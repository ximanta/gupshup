package com.stackroute.gupshup.recommendationservice.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.recommendationservice.entity.UserRecommendation;

public interface UserRecommendationService {
	
	public Map<String, Object> createUser(UserRecommendation userRecommendation);
	public Iterable<Map<String, Object>> follows(String user1, String user2);
	public Iterable<List<String>> followFriendOfFriend(String user);
	public void getActivityType(JsonNode node);
}
