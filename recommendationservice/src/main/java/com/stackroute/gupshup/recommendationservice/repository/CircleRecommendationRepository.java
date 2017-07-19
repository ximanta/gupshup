package com.stackroute.gupshup.recommendationservice.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.gupshup.recommendationservice.entity.CircleRecommendation;

/*-----Class to connect to neo4j to make circle nodes---------*/
@Repository
public interface CircleRecommendationRepository extends GraphRepository<CircleRecommendation> {
	
	/*------query to create a circle node in neo4j-------*/
	@Query("merge (c:circle {circleId:{0}, keyword:{1}, circleName:{2}, createdBy:{3}}) return c")
	Map<String, Object> createCircle(String circleId, List<String> keyword, String CircleName, String createdBy);
	
	/*---------query to delete a circle node in neo4j--------*/
	@Query("MATCH (a:circle {circleId: {0}}) DETACH DELETE a")
	void deleteCircle(String circleId);
	
	/*---------query to update circle properties in neo4j--------*/
	@Query("MATCH (a:circle {circleId: {0}}) SET a.keyword = {1}, a.circleName = {2}, a.createdBy = {3} RETURN a")
	Map<String, Object> updateCircle(String circleId, List<String> keyword, String CircleName, String createdBy);
	
	/*----------query to create a created relationship in neo4j when user creates a circle-------*/
	@Query("match (a:person), (b:circle) where a.name={0} and b.circleId={1} create (a)-[:created]->(b) return a,b")
	Iterable<Map<String, Object>> created(String name1, String circleId);
	
	/*----------query to create a subscribed relationship in neo4j when user subscribes to a circle-------*/
	@Query("match (a:person), (b:circle) where a.name={0} and b.circleId={1} create (a)-[:subscribed]->(b) return a,b")
	Iterable<Map<String, Object>> subscribed(String name1, String circleId);
	
	/*---------circle subscribe recommendation for a user---------*/
	@Query("match (a:person {name:{0}})-[:follows]->(people),(people)-[:created |:subscribed]->(things) where not (a)-[:subscribed |:created]->(things) return distinct things.circleId AS circleId, things.circleName AS circleName")
	Iterable<List<Map<String,String>>> subscribeRecommendation(String user);
	
	/*--------query to delete subscribe relationship in neo4j when user wants to leave a circle-------*/
	@Query("match (a:person)-[r:subscribed]->(b:circle) where a.name={0} and b.circleId={1} delete r")
	void leaveCircle(String name, String circleId);

	/*---------query to get the circle ID--------*/
	@Query("match (n:circle {circleId:{0}}) return n.circleId")
	String findByName(String circleId);
	
	/*--------query to get circle properties through circle ID------*/
	@Query("match (n:circle {circleId:{0}}) return n")
	CircleRecommendation findCircle(String circleId);
}

