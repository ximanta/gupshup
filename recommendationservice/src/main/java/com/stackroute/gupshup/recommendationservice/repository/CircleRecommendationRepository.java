package com.stackroute.gupshup.recommendationservice.repository;

import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.gupshup.recommendationservice.entity.CircleRecommendation;


@Repository
public interface CircleRecommendationRepository extends GraphRepository<CircleRecommendation> {
	
	@Query("merge (blue:circle {keyword:{0}, type:{1}}) return blue")
	Map<String, Object> createCircle(String keyword, String type);
	
	@Query("match (a:person), (b:circle) where a.name={0} and b.keyword={1} create (a)-[:created]->(b) return a,b")
	Iterable<Map<String, Object>> created(String name1, String keyword);
	
	@Query("match (a:person), (b:circle) where a.name={0} and b.keyword={1} create (a)-[:subscribed]->(b) return a,b")
	Iterable<Map<String, Object>> subscribed(String name1, String keyword);
	
//	List<CircleRecommendation> findByName(String name);


}
