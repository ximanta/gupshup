package com.stackroute.gupshup.circleservice.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stackroute.gupshup.circleservice.exception.CircleException;
import com.stackroute.gupshup.circleservice.model.Circle;
import com.stackroute.gupshup.circleservice.model.Mailbox;
import com.stackroute.gupshup.circleservice.model.Member;

public interface CircleService {
	
	public List<Circle> listAllCircles() throws CircleException;
	public Circle findByID(String circleId);
	public Circle createCircle(Circle circle) throws CircleException,JsonProcessingException;
	public Circle updateCircle(Circle circle) throws CircleException,JsonProcessingException;
	public void deleteCircle(String circleId) throws CircleException;
	
	public void addCircleMember(Member member);
	public void addMails( Mailbox mailbox );
	
	public List<Mailbox> getMails(String circleId, String userName, int page) throws CircleException;
	public List<Member> getMembers(String circleId) throws CircleException;
	public List<Member> getCircles(String username) throws CircleException;
	 
	public void getActivityType(String activity) throws CircleException;
	public void changeStatus(String userName);
}
	