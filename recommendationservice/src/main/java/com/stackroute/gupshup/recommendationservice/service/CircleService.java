package com.stackroute.gupshup.recommendationservice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.gupshup.recommendationservice.repository.CircleRepository;

@Service
public class CircleService {
	
	@Autowired
	CircleRepository circleRepository;
	
	public Map<String, Object> createCircle(){
		
		return circleRepository.getFlash("blue", "public");
	}
	
	public Iterable<Map<String, Object>> createCreatedRelation(){
		return circleRepository.getCreatedRelationship("ayu18", "blue");
	}
	
	public Iterable<Map<String, Object>> createSubscribedRelation(){
		return circleRepository.getSubscribedRelationship("ay21", "blue");
	}

}
