package com.stackroute.gupshup.mailboxservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stackroute.gupshup.mailboxservice.model.Mailbox;

public interface MailboxRepository extends MongoRepository<Mailbox, String>{

	public Mailbox findOneByUsername(String userName);

}
