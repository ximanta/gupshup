package com.stackroute.gupshup.circleservice.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.circleservice.exception.CircleCreationException;
import com.stackroute.gupshup.circleservice.model.*;

public interface CircleService {
	
	public Circle createCircle(Circle circle) throws CircleCreationException;
	public void deleteCircle(String id) throws CircleCreationException;
	public List<Circle> findAllCircle() throws CircleCreationException;
	public Circle findById(String id) throws CircleCreationException;
	public void updateCircle(Circle currentCircle) throws CircleCreationException;
	public void deleteAllCircle() throws CircleCreationException;
	public boolean ifCircleExist(Circle circle) throws CircleCreationException;
	public List<User> getCircleMembersDetail(String circleId) throws CircleCreationException;
	public Circle addCircleMember(String circleId, User user) throws CircleCreationException;
	public Circle deleteCircleMember(String circleId, String userId) throws CircleCreationException;
	public void getActivityType(JsonNode node);
	public void addMailtoMailbox(String circleId,Mail mail) throws CircleCreationException;
	
}
