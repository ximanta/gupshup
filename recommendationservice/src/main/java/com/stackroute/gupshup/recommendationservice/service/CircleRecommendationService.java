package com.stackroute.gupshup.recommendationservice.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.recommendationservice.entity.CircleRecommendation;

public interface CircleRecommendationService {
	
	public Map<String, Object> createCircle(CircleRecommendation circleRecommendation);
	public String deleteCircle(String circleId);
	public Map<String, Object> updateCircle(CircleRecommendation circleRecommendation);
	//public Iterable<Map<String, Object>> created(String user, String circle);
	public Iterable<Map<String, Object>> subscribed(String user, String circle);
	public String leaveCircle(String name, String circleId);
	public Iterable<List<String>> subscribeRecommendation(String user);
	public void getActiviType(JsonNode node);
}
