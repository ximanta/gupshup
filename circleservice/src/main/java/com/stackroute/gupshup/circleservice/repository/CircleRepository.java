package com.stackroute.gupshup.circleservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.stackroute.gupshup.circleservice.model.Circle;

public interface CircleRepository extends MongoRepository<Circle, String> {
	
	@Query("{circlename:?0}")
	public Circle findByCircleName(String circleName);
}
