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
		Map<String, Object> circle = circleRecommendationRepository.createCircle(circleRecommendation.getCircleId(), circleRecommendation.getKeyword(), circleRecommendation.getCircleName(), circleRecommendation.getCreatedBy());
		circleRecommendationRepository.created(circleRecommendation.getCreatedBy(), circleRecommendation.getCircleId());
		return circle;
	}
	
	@Override
	public String deleteCircle(String circleId)
	{
		circleRecommendationRepository.deleteCircle(circleId);
		return "circle deleted";
	}
	
	@Override
	public Map<String, Object> updateCircle(CircleRecommendation circleRecommendation){
		
		System.out.println("circle updated");
		System.out.println(circleRecommendation);
		return circleRecommendationRepository.updateCircle(circleRecommendation.getCircleId(), circleRecommendation.getKeyword(), circleRecommendation.getCircleName(), circleRecommendation.getCreatedBy());
	}
	
	
	/*@Override
	public Iterable<Map<String, Object>> created(String user, String circleId){
		
		System.out.println("service:"+user+" "+circleId);
		System.out.println("created relationship");
		return circleRecommendationRepository.created(user, circleId);
	}*/
	
	@Override
	public Iterable<Map<String, Object>> subscribed(String user, String circleId){
		System.out.println("user subscribed");
		return circleRecommendationRepository.subscribed(user, circleId);
	}
	
	@Override
	public String leaveCircle(String name, String circleId){
		circleRecommendationRepository.leaveCircle(name, circleId);
		return name+" left "+ circleId;
		
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
			String circleId = objectType.path("circleId").asText();
			String circleName = objectType.path("circleName").asText();
			String keyword = objectType.path("keyword").asText();
			String createdBy = objectType.path("createdBy").asText();
		
			if(user=="" || circleId=="" || circleName=="" || keyword=="" || createdBy=="")
			{
				System.out.println("Create: userame, circlename or keyword field is empty");
				System.out.println("user: "+user+" circleId: "+circleId+" circleName: "+circleName+" keyword: "+keyword+" createdBy: "+createdBy);
			}
			else
			{
				System.out.println("create circle");
				CircleRecommendation circleRecommendation = new CircleRecommendation();
				
				circleRecommendation.setCircleId(circleId);
				circleRecommendation.setCircleName(circleName);
				circleRecommendation.setKeyword(keyword);
				circleRecommendation.setCreatedBy(createdBy);
				
				circleRecommendationService.createCircle(circleRecommendation);
				//circleRecommendationService.created(user, circleId);
			}
		}
		
		if(activityType.equalsIgnoreCase("Join") && actorType.equalsIgnoreCase("Person") && objType.equalsIgnoreCase("Group"))
		{
		
			String user = actor.path("name").asText();
			String circleId = objectType.path("circleId").asText();
		
			if(user=="" || circleId=="")
			{
				System.out.println("Join: userame or circlename Field is empty");
			}
			else
			{
				circleRecommendationService.subscribed(user, circleId);
			}
		}
		
		if(activityType.equalsIgnoreCase("Leave") && actorType.equalsIgnoreCase("Person") && objType.equalsIgnoreCase("Group"))
		{
		
			String name = actor.path("name").asText();
			String circleId = objectType.path("circleId").asText();
		
			if(name=="" || circleId=="")
			{
				System.out.println("Join: userame or circlename Field is empty");
			}
			else
			{
				circleRecommendationService.leaveCircle(name, circleId);
			}
		}
		
		if(activityType.equalsIgnoreCase("Update") && actorType.equalsIgnoreCase("Person") && objType.equalsIgnoreCase("Group"))
		{
		
			String user = actor.path("name").asText();
			String circleId = objectType.path("circleId").asText();
			String circleName = objectType.path("circleName").asText();
			String keyword = objectType.path("keyword").asText();
			String createdBy = objectType.path("createdBy").asText();
		
			if(user=="" || circleId=="" || circleName=="" || keyword=="" || createdBy=="")
			{
				System.out.println("Update: userame, circlename or keyword field is empty");
				System.out.println("user: "+user+" circleId: "+circleId+" circleName: "+circleName+" keyword: "+keyword+" createdBy: "+createdBy);
			}
			else
			{
				System.out.println("update circle");
				CircleRecommendation circleRecommendation = new CircleRecommendation();
				
				circleRecommendation.setCircleId(circleId);
				circleRecommendation.setCircleName(circleName);
				circleRecommendation.setKeyword(keyword);
				circleRecommendation.setCreatedBy(createdBy);
				
				circleRecommendationService.updateCircle(circleRecommendation);
			}
		}
		
		if(activityType.equalsIgnoreCase("Delete") && actorType.equalsIgnoreCase("Person") && objType.equalsIgnoreCase("Group"))
		{
		
			String user = actor.path("name").asText();
			String circleId = objectType.path("circleId").asText();
		
			if(user=="" || circleId=="")
			{
				System.out.println("Delete: userame or circlename Field is empty");
			}
			else
			{
				circleRecommendationService.deleteCircle(circleId);
			}
		}
		
		
	}
}
