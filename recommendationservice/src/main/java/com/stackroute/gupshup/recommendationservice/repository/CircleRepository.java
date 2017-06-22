package com.stackroute.gupshup.recommendationservice.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.stackroute.gupshup.recommendationservice.entity.Circle;


public interface CircleRepository extends GraphRepository<Circle> {
	
	@Query("merge (blue:circle {keyword:{0}, type:{1}}) return blue")
	Map<String, Object> getFlash(String keyword, String type);
	
	@Query("match (a:person), (b:circle) where a.name={0} and b.keyword={1} create (a)-[:created]->(b) return a,b")
	Iterable<Map<String, Object>> getCreatedRelationship(String name1, String keyword);
	
	@Query("match (a:person), (b:circle) where a.name={0} and b.keyword={1} create (a)-[:subscribed]->(b) return a,b")
	Iterable<Map<String, Object>> getSubscribedRelationship(String name1, String keyword);
	
	List<Circle> findByName(String name);


}
