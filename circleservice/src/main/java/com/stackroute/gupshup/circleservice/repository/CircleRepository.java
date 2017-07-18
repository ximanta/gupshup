package com.stackroute.gupshup.circleservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stackroute.gupshup.circleservice.model.Circle;

public interface CircleRepository extends MongoRepository<Circle, String> {
	
	public Circle findByCircleName(String circleName);
}
