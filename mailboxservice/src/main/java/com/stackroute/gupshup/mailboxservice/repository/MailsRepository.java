package com.stackroute.gupshup.mailboxservice.repository;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.gupshup.mailboxservice.model.Mails;


@Repository
public interface MailsRepository extends MongoRepository<Mails, String>
{
	
}



