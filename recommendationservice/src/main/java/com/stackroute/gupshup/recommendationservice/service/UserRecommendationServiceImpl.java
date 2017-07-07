package com.stackroute.gupshup.recommendationservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.recommendationservice.entity.UserRecommendation;
import com.stackroute.gupshup.recommendationservice.exception.RecommendationException;
import com.stackroute.gupshup.recommendationservice.repository.UserRecommendationRepository;

@Service
public class UserRecommendationServiceImpl implements UserRecommendationService {
	
	@Autowired
	UserRecommendationRepository userRecommendationRepository;
	
	@Autowired
	UserRecommendationService userRecommendationService;
	
	@Override
	public Map<String, Object> createUser(UserRecommendation userRecommendation) throws RecommendationException{
		String u = userRecommendationRepository.findByName(userRecommendation.getName());
		if(u!=null)
		{
			System.out.println(u);
			throw new RecommendationException("Username already exists");
		}
		else{
		System.out.println("Create Service:"+userRecommendation);
				return userRecommendationRepository.createUser(
				userRecommendation.getName(),
				userRecommendation.getFirstname(),
				userRecommendation.getLastname(),
				userRecommendation.getGender(),
				userRecommendation.getIntrest(),
				userRecommendation.getDOB());
		}
	}
	
	@Override
	public String deleteUser(String user) throws RecommendationException{
		String u = userRecommendationRepository.findByName(user);
		if(u==null)
		{
			System.out.println("delete"+u);
			throw new RecommendationException("Username does not exist");
		}
		else{
		userRecommendationRepository.deleteUserCreatedCircles(user);
		userRecommendationRepository.deleteUser(user);
		return "user deleted";
		}
	}
	
	@Override
	public Map<String, Object> updateUser(UserRecommendation userRecommendation) throws RecommendationException{
		System.out.println(userRecommendation);
		String u = userRecommendationRepository.findByName(userRecommendation.getName());
		if(u==null)
		{
			System.out.println("update"+u);
			throw new RecommendationException("Username does not exist");
		}
		else{
		return userRecommendationRepository.updateUser(
				userRecommendation.getName(),
				userRecommendation.getFirstname(),
				userRecommendation.getLastname(),
				userRecommendation.getGender(),
				userRecommendation.getIntrest(),
				userRecommendation.getDOB());
		}
	}
	
	@Override
	public Iterable<Map<String, Object>> follows(String user1, String user2) throws RecommendationException{
		String u1 = userRecommendationRepository.findByName(user1);
		String u2 = userRecommendationRepository.findByName(user2);
		if(u1==null||u2==null)
		{
			System.out.println("follow:"+u1+" "+u2);
			throw new RecommendationException("Username does not exist");
		}
		else{
		System.out.println("service "+user1+" "+user2);
		return userRecommendationRepository.follows(user1, user2);
		}
	}
	
	@Override
	public Iterable<List<String>> followRecommendation(String user) throws RecommendationException{
		String u = userRecommendationRepository.findByName(user);
		if(u==null||user==null)
		{
			System.out.println("follow recomm:"+u);
			throw new RecommendationException("Username does not exist");
		}
		else
		{
		return userRecommendationRepository.followPeople(user);
		}
	}
	
	@Override
	public void getActivityType(JsonNode node)
	{
		System.out.println("user: entering activity");
		String activityType = node.path("type").asText();
		System.out.println("user: activity:"+activityType);
		JsonNode actor = node.path("actor");
		String actorType = actor.path("type").asText();
		System.out.println("user: actor: "+actorType);
		JsonNode objectType = node.path("object");
		//String objType = objectType.path("type").asText();
		//System.out.println("user: object: "+objType);
		
		if(activityType.equalsIgnoreCase("Create") && actorType.equalsIgnoreCase("Person"))
		{
		
			String name = actor.path("name").asText();
			String firstname = actor.path("firstname").asText();
			String lastname = actor.path("lastname").asText();
			String gender = actor.path("gender").asText();
			String intrest = actor.path("intrest").asText();
			String DOB = actor.path("DOB").asText();
		
			if(name==""||firstname==""||lastname==""||gender==""||intrest==""||DOB=="")
			{
				System.out.println("Create: Empty Fields");
			}
			else
			{
				System.out.println("create user");
				UserRecommendation userRecommendation = new UserRecommendation();
				int dob = Integer.parseInt(DOB);
				userRecommendation.setDOB(dob);
				userRecommendation.setFirstname(firstname);
				userRecommendation.setGender(gender);
				userRecommendation.setIntrest(intrest);
				userRecommendation.setLastname(lastname);
				userRecommendation.setName(name);
			
				try {
					userRecommendationService.createUser(userRecommendation);
				} catch (RecommendationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		if(activityType.equalsIgnoreCase("Follow") && actorType.equalsIgnoreCase("Person"))
		{
		
			String user1 = actor.path("name").asText();
			String user2 = objectType.path("name").asText();
		
			if(user1==""||user2=="")
			{
				System.out.println("Follow: Name Field is empty");
			}
			else
			{
				try {
					userRecommendationService.follows(user1, user2);
				} catch (RecommendationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(activityType.equalsIgnoreCase("Update") && actorType.equalsIgnoreCase("Person"))
		{
			String name = actor.path("name").asText();
			String firstname = actor.path("firstname").asText();
			String lastname = actor.path("lastname").asText();
			String gender = actor.path("gender").asText();
			String intrest = actor.path("intrest").asText();
			String DOB = actor.path("DOB").asText();
		
			if(name==""||firstname==""||lastname==""||gender==""||intrest==""||DOB=="")
			{
				System.out.println("Update: Empty Fields");
			}
			else
			{
				UserRecommendation userRecommendation = new UserRecommendation();
				int dob = Integer.parseInt(DOB);
				userRecommendation.setDOB(dob);
				userRecommendation.setFirstname(firstname);
				userRecommendation.setGender(gender);
				userRecommendation.setIntrest(intrest);
				userRecommendation.setLastname(lastname);
				userRecommendation.setName(name);
				
				try {
					userRecommendationService.updateUser(userRecommendation);
				} catch (RecommendationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(activityType.equalsIgnoreCase("Delete") && actorType.equalsIgnoreCase("Person"))
		{
		
			String user = actor.path("name").asText();
		
			if(user=="")
			{
				System.out.println("Follow: Name Field is empty");
			}
			else
			{
				try {
					userRecommendationService.deleteUser(user);
				} catch (RecommendationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
