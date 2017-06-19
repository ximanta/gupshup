package com.stackroute.gupshup.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.gupshup.userservice.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

}
