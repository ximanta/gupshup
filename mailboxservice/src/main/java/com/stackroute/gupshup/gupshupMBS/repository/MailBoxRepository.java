package com.stackroute.gupshup.gupshupMBS.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.gupshup.gupshupMBS.model.Mailbox;

@Repository
public interface MailBoxRepository  extends MongoRepository<Mailbox, String>
{
}



