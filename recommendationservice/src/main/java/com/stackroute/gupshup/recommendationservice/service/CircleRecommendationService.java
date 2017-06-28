package com.stackroute.gupshup.recommendationservice.service;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.recommendationservice.entity.CircleRecommendation;

public interface CircleRecommendationService {
	
	public Map<String, Object> createCircle(CircleRecommendation circleRecommendation);
	public Iterable<Map<String, Object>> created(String user, String circle);
	public Iterable<Map<String, Object>> subscribed(String user, String circle);
	public void getActiviType(JsonNode node);
}
