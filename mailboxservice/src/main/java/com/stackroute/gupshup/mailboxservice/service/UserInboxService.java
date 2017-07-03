package com.stackroute.gupshup.mailboxservice.service;
 
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.mailboxservice.model.Mailbox;
import com.stackroute.gupshup.mailboxservice.model.Mails;


public interface UserInboxService
{


	public Mailbox createUser(JsonNode node);
	public void deleteUser(JsonNode node);
	//public Mailbox updateUser(JsonNode node);
	public List<Mails> followUser(JsonNode node);
	//public Mailbox directMessage(JsonNode node);

}

