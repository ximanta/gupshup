package com.stackroute.gupshup.mailboxservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.stackroute.gupshup.mailboxservice.model.Mails;

public interface MailsRepository extends MongoRepository<Mails, String>{
	
	@Query("{username:?0, circleID:?1}")
	public List<Mails> findAll(String userName, String circleID);
	
	public Mails findByMailID(String mailId);
}
