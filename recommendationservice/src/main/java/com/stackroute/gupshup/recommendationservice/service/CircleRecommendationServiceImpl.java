package com.stackroute.gupshup.recommendationservice.service;

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.recommendationservice.entity.CircleRecommendation;
import com.stackroute.gupshup.recommendationservice.entity.UserRecommendation;
import com.stackroute.gupshup.recommendationservice.exception.RecommendationException;
import com.stackroute.gupshup.recommendationservice.repository.CircleRecommendationRepository;
import com.stackroute.gupshup.recommendationservice.repository.UserRecommendationRepository;

@Service
public class CircleRecommendationServiceImpl implements CircleRecommendationService {
	
	@Autowired
	CircleRecommendationRepository circleRecommendationRepository;
	
	@Autowired
	UserRecommendationRepository userRecommendationRepository;
	
	@Autowired
	CircleRecommendationService circleRecommendationService;
	
	@Override
	public Map<String, Object> createCircle(CircleRecommendation circleRecommendation) throws RecommendationException{
		
		String s = circleRecommendationRepository.findByName(circleRecommendation.getCircleId());
		Map<String, Object> circle;
		if(s != null)
		{
			throw new RecommendationException("circle ID already exists");
		}
		else{
		System.out.println("circle created");
		circle = circleRecommendationRepository.createCircle(circleRecommendation.getCircleId(), circleRecommendation.getKeyword(), circleRecommendation.getCircleName(), circleRecommendation.getCreatedBy());
		circleRecommendationRepository.created(circleRecommendation.getCreatedBy(), circleRecommendation.getCircleId());
		}
		return circle;
	}
	
	@Override
	public String deleteCircle(String circleId) throws RecommendationException{
		String c = circleRecommendationRepository.findByName(circleId);
		if(c==null)
		{
			throw new RecommendationException("Circle ID does not exist");
		}
		circleRecommendationRepository.deleteCircle(circleId);
		return "circle deleted";
	}
	
	@Override
	public Map<String, Object> updateCircle(CircleRecommendation circleRecommendation) throws RecommendationException{
		
		String c = circleRecommendationRepository.findByName(circleRecommendation.getCircleId());
		if(c==null)
		{
			throw new RecommendationException("Circle ID does not exist");
		}
		else
		{
		System.out.println("circle updated");
		System.out.println(circleRecommendation);
		return circleRecommendationRepository.updateCircle(circleRecommendation.getCircleId(), circleRecommendation.getKeyword(), circleRecommendation.getCircleName(), circleRecommendation.getCreatedBy());
		}
	}
	
	@Override
	public Iterable<Map<String, Object>> subscribed(String user, String circleId) throws RecommendationException{
		String c = circleRecommendationRepository.findByName(circleId);
		String u = userRecommendationRepository.findByName(user);
		if(c==null||u==null)
		{
			throw new RecommendationException("Circle ID does not exist");
		}
		else{
		System.out.println("user subscribed");
		return circleRecommendationRepository.subscribed(user, circleId);
		}
	}
	
	@Override
	public String leaveCircle(String name, String circleId) throws RecommendationException{
		String u = userRecommendationRepository.findByName(name);
		String c = circleRecommendationRepository.findByName(circleId);
		if(c==null||u==null)
		{
			throw new RecommendationException("Circle ID or Username does not exist");
		}
		else{
		circleRecommendationRepository.leaveCircle(name, circleId);
		return name+" left "+ circleId;
		}
		
	}
	
	@Override
	public Iterable<List<String>> subscribeRecommendation(String user) throws RecommendationException{
		String u = userRecommendationRepository.findByName(user);
		if(u==null)
		{
			throw new RecommendationException("Username does not exist");
		}
		else{
		return circleRecommendationRepository.subscribeRecommendation(user);
		}
	}
	
	@Override
	public CircleRecommendation findCircle(String circleId){
		return circleRecommendationService.findCircle(circleId);
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
			String k= objectType.path("keyword").asText();
			List<String> keyword = new ArrayList<String>(Arrays.asList(k.split(",")));
			String createdBy = objectType.path("createdBy").asText();
		
			if(user=="" || circleId=="" || circleName=="" || keyword==null || createdBy=="")
			{
				System.out.println("Create: userame, circlename or keyword field is empty");
				System.out.println("user: "+user+" circleId: "+circleId+" circleName: "+circleName+" keyword: "+keyword+" createdBy: "+createdBy);
			} else
				try {
					{
						System.out.println("create circle");
						CircleRecommendation circleRecommendation = new CircleRecommendation();
						
						circleRecommendation.setCircleId(circleId);
						circleRecommendation.setCircleName(circleName);
						circleRecommendation.setKeyword(keyword);
						circleRecommendation.setCreatedBy(createdBy);
						
						circleRecommendationService.createCircle(circleRecommendation);
					}
				} catch (RecommendationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				try {
					circleRecommendationService.subscribed(user, circleId);
				} catch (RecommendationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				try {
					circleRecommendationService.leaveCircle(name, circleId);
				} catch (RecommendationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(activityType.equalsIgnoreCase("Update") && actorType.equalsIgnoreCase("Person") && objType.equalsIgnoreCase("Group"))
		{
		
			String user = actor.path("name").asText();
			String circleId = objectType.path("circleId").asText();
			String circleName = objectType.path("circleName").asText();
			String k= objectType.path("keyword").asText();
			List<String> keyword = new ArrayList<String>(Arrays.asList(k.split(",")));
			String createdBy = objectType.path("createdBy").asText();
		
			if(user=="" || circleId=="" || circleName=="" || keyword==null || createdBy=="")
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
				
				try {
					circleRecommendationService.updateCircle(circleRecommendation);
				} catch (RecommendationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				try {
					circleRecommendationService.deleteCircle(circleId);
				} catch (RecommendationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
}
