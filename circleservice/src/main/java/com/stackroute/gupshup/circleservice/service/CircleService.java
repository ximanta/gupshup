package com.stackroute.gupshup.circleservice.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.circleservice.exception.CircleException;
import com.stackroute.gupshup.circleservice.model.*;

public interface CircleService {
	
	public Circle createCircle(Circle circle) throws CircleException,JsonProcessingException;
	public void deleteCircle(String circleId) throws CircleException,JsonProcessingException;
	public List<Circle> findAllCircles() throws CircleException;
	public Circle findById(String circleId) throws CircleException;
	public void updateCircle(Circle circle) throws CircleException,JsonProcessingException;
	public void deleteAllCircle() throws CircleException;
	
	public List<User> getCircleMembers(String circleId) throws CircleException;
	public Circle addCircleMember(String circleId, User user) throws CircleException;
	public Circle deleteCircleMember(String circleId, String userName) throws CircleException;

	public void addMailtoMailbox(String circleId,Mail mail) throws CircleException;
	
	public void getActivityType(JsonNode node) throws CircleException,JsonProcessingException;
	
	
}
