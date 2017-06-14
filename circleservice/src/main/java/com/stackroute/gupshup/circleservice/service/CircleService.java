package com.stackroute.gupshup.circleservice.service;

import java.util.List;

import com.stackroute.gupshup.circleservice.exception.CircleCreationException;
import com.stackroute.gupshup.circleservice.model.*;

public interface CircleService {
	public Circle createCircle(Circle circle) throws CircleCreationException;

	public void deleteCircle(String id) throws CircleCreationException;

	public List<Circle> findAllCircle() throws CircleCreationException;

	public Circle findById(String id) throws CircleCreationException;

	public void updateCircle(Circle currentCircle) throws CircleCreationException;

	void deleteAllCircle() throws CircleCreationException;

	boolean ifCircleExist(Circle circle) throws CircleCreationException;

	
	
}
