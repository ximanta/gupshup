package com.stackroute.gupshup.circleservice.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.stackroute.gupshup.circleservice.model.Mailbox;

public interface MailboxRepository extends MongoRepository<Mailbox, String> {
	
	@Query("{circleId:?0}")
	public List<Mailbox> findAllMails(String circleId,Pageable pageable);

}
