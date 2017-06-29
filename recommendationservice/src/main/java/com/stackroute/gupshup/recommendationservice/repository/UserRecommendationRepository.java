package com.stackroute.gupshup.recommendationservice.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.gupshup.recommendationservice.entity.UserRecommendation;

@Repository
public interface UserRecommendationRepository extends GraphRepository<UserRecommendation> {
	
@Query("merge (ay19:person {name:{0}, firstname:{1}, lastname:{2}, gender:{3},intrest:{4}, DOB:{5}}) return ay19")
Map<String, Object> createUser(String name, String firstname, String lastname, String gender, String intrest, int DOB);

@Query("MATCH (a:circle {createdBy:{0}}) DETACH DELETE a")
void deleteUserCreatedCircles(String createdBy);

@Query("MATCH (a:person {name:{0}}) DETACH DELETE a")
void deleteUser(String user);

@Query("MATCH (a:person{name: {0}}) SET a.firstname = {1}, a.lastname = {2}, a.gender = {3}, a.intrest = {4}, a.DOB = {5} RETURN a")
Map<String, Object> updateUser(String name, String firstname, String lastname, String gender, String intrest, int DOB);

@Query("match (a:person), (b:person) where a.name={0} and b.name={1} create (a)-[:follows]->(b) return a,b")
Iterable<Map<String, Object>> follows(String name1, String name2);

@Query("match (a:person {name:{0}})-[:follows]->(people), (people)-[:follows]->(morepeople) where not (a)-[:follows]->(morepeople) return morepeople.name")
Iterable<List<String>> followFriendOfFriend(String user);

List<UserRecommendation> findByName(String name);
}
 