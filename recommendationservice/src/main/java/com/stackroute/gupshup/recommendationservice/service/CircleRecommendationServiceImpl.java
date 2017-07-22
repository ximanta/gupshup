package com.stackroute.gupshup.recommendationservice.service;

import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.recommendationservice.entity.CircleRecommendation;
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
	
	/*------method to create a circle node in neo4j-------*/
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
		circle = circleRecommendationRepository.createCircle(circleRecommendation.getCircleId(), circleRecommendation.getKeywords(), circleRecommendation.getCircleName(), circleRecommendation.getCreatedBy());
		circleRecommendationRepository.created(circleRecommendation.getCreatedBy(), circleRecommendation.getCircleId());
		}
		return circle;
	}
	
	/*------method to delete a circle node in neo4j-------*/
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
	
	/*------method to update a circle node in neo4j-------*/
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
		return circleRecommendationRepository.updateCircle(circleRecommendation.getCircleId(), circleRecommendation.getKeywords(), circleRecommendation.getCircleName(), circleRecommendation.getCreatedBy());
		}
	}
	
	/*----------method to create a subscribed relationship in neo4j when user subscribes to a circle-------*/
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
	
	/*--------method to delete subscribe relationship in neo4j when user wants to leave a circle-------*/
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
	
	/*---------circle subscribe recommendation for a user---------*/
	@Override
	public List<Map<String,String>> subscribeRecommendation(String user) throws RecommendationException{
		String u = userRecommendationRepository.findByName(user);
		
		if(u==null)
		{
			throw new RecommendationException("Username does not exist");
		}
		else{
			ArrayList<Map<String,String>> list =  circleRecommendationRepository.subscribeRecommendation(user);
			return list;
		}
	}
	
	/*--------query to get circle properties through circle ID------*/
	@Override
	public CircleRecommendation findCircle(String circleId){
		return circleRecommendationService.findCircle(circleId);
	}
	
	/*---------method to check activity type of activity stream when json is consumed through kafka-----------*/
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
		
			String circleId = objectType.path("id").asText();
			String circleName = objectType.path("name").asText();
			ArrayList<String> keyword = new ArrayList<String>();
			Iterator<JsonNode> elements = objectType.path("keywords").elements();
			while(elements.hasNext()) {
				keyword.add(elements.next().asText());
			}
			String createdBy = actor.path("id").asText();
		
			if(circleId=="" || circleName=="" || keyword==null || createdBy=="")
			{
				System.out.println("Create: userame, circlename or keyword field is empty");
				System.out.println("circleId: "+circleId+" circleName: "+circleName+" keyword: "+keyword+" createdBy: "+createdBy);
			} else
				try {
					{
						System.out.println("create circle");
						CircleRecommendation circleRecommendation = new CircleRecommendation();
						
						circleRecommendation.setCircleId(circleId);
						circleRecommendation.setCircleName(circleName);
						circleRecommendation.setKeywords(keyword);
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
		
			String user = actor.path("id").asText();
			String circleId = objectType.path("id").asText();
		
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
		
			String name = actor.path("id").asText();
			String circleId = objectType.path("id").asText();
		
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
		
			String circleId = objectType.path("id").asText();
			String circleName = objectType.path("name").asText();
			ArrayList<String> keyword = new ArrayList<String>();
			Iterator<JsonNode> elements = objectType.path("keywords").elements();
			while(elements.hasNext()) {
				keyword.add(elements.next().asText());
			}
			String createdBy = actor.path("id").asText();
		
			if(circleId=="" || circleName=="" || keyword==null || createdBy=="")
			{
				System.out.println("Update: userame, circlename or keyword field is empty");
				System.out.println("circleId: "+circleId+" circleName: "+circleName+" keyword: "+keyword+" createdBy: "+createdBy);
			}
			else
			{
				System.out.println("update circle");
				CircleRecommendation circleRecommendation = new CircleRecommendation();
				
				circleRecommendation.setCircleId(circleId);
				circleRecommendation.setCircleName(circleName);
				circleRecommendation.setKeywords(keyword);
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
		
			String user = actor.path("id").asText();
			String circleId = objectType.path("id").asText();
		
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

