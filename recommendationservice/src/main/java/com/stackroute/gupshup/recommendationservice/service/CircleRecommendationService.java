package com.stackroute.gupshup.recommendationservice.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.recommendationservice.entity.CircleRecommendation;
import com.stackroute.gupshup.recommendationservice.exception.RecommendationException;

public interface CircleRecommendationService {
	
	public Map<String, Object> createCircle(CircleRecommendation circleRecommendation) throws RecommendationException;
	public String deleteCircle(String circleId) throws RecommendationException;
	public Map<String, Object> updateCircle(CircleRecommendation circleRecommendation) throws RecommendationException;
	public Iterable<Map<String, Object>> subscribed(String user, String circle) throws RecommendationException;
	public String leaveCircle(String name, String circleId) throws RecommendationException;
	public Iterable<List<Map<String,String>>> subscribeRecommendation(String user) throws RecommendationException;
	public CircleRecommendation findCircle(String circleId);
	public void getActiviType(JsonNode node);
}

