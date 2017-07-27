package com.stackroute.gupshup.recommendationservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.gupshup.recommendationservice.entity.UserRecommendation;

/*-----Class to connect to neo4j to make user nodes---------*/
@Repository
public interface UserRecommendationRepository extends GraphRepository<UserRecommendation> {

/*------query to create a user node in neo4j-------*/
@Query("merge (ay19:person {name:{0}, firstname:{1}, lastname:{2}, gender:{3},intrest:{4}, DOB:{5}}) return ay19")
Map<String, Object> createUser(String name, String firstname, String lastname, String gender, List<String> intrest, String DOB);

/*---------query to delete a user created circles in neo4j------*/
@Query("MATCH (a:circle {createdBy:{0}}) DETACH DELETE a")
void deleteUserCreatedCircles(String createdBy);

/*---------query to delete a user node in neo4j--------*/
@Query("MATCH (a:person {name:{0}}) DETACH DELETE a")
void deleteUser(String user);

/*---------query to update user properties in neo4j--------*/
@Query("MATCH (a:person{name: {0}}) SET a.firstname = {1}, a.lastname = {2}, a.gender = {3}, a.intrest = {4}, a.DOB = {5} RETURN a")
Map<String, Object> updateUser(String name, String firstname, String lastname, String gender, List<String> intrest, String DOB);

/*----------query for one user to follow another user------*/
@Query("match (a:person), (b:person) where a.name={0} and b.name={1} create (a)-[:follows]->(b) return a,b")
Iterable<Map<String, Object>> follows(String name1, String name2);

/*---------recommendation query to follow people subscribed to same circle--------*/
@Query("match (n:person {name:{0}}),(n)-[:created|:subscribed]->(things)<-[:created|:subscribed]-(people:person) where not (n)-[:follows]->(people) and not people.name=n.name return distinct people.name AS name, people.firstname AS firstname, people.lastname AS lastname")
ArrayList<Map<String,String>> followSameCirclePeople(String user);

/*--------recommendation query to follow friend of friend with distinct results-----*/
@Query("match (a:person {name:{0}})-[:follows]->(people), (people)-[:follows]->(morepeople) where not (a)-[:follows]->(morepeople) and not morepeople=a return distinct morepeople.name AS name, morepeople.firstname AS firstname, morepeople.lastname AS lastname")
ArrayList<Map<String,String>> followPeople(String user);

/*-------query to get name of the user through username from neo4j---------*/
@Query("match (n:person {name:{0}}) return n.name")
String findByName(String name);

/*-------query to get propeties of the user through username from neo4j---------*/
@Query("match (n:person {name:{0}}) return n")
UserRecommendation findUser(String name);
}
 
