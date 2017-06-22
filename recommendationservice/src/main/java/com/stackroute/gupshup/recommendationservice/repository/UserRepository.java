package com.stackroute.gupshup.recommendationservice.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.gupshup.recommendationservice.entity.User;

@Repository
public interface UserRepository extends GraphRepository<User> {
	
@Query("merge (ay19:person {name:{0}, firstname:{1}, lastname:{2}, gender:{3},intrest:{4}, DOB:{5}}) return ay19")
Map<String, Object> getTrios(String name, String firstname, String lastname, String gender, String intrest, int DOB);

@Query("match (a:person), (b:person) where a.name={0} and b.name={1} create (a)-[:follows]->(b) return a,b")
Iterable<Map<String, Object>> getRelation(String name1, String name2);

List<User> findByName(String name);
}
 