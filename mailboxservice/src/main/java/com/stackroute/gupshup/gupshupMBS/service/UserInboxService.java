package com.stackroute.gupshup.gupshupMBS.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.gupshupMBS.model.Mailbox;

public interface UserInboxService
{


	public Mailbox createUser(JsonNode node);
	public void deleteUser(JsonNode node);
	public Mailbox updateUser(JsonNode node);
	public List<Mailbox> followUser(JsonNode node);
	public Mailbox directMessage(JsonNode node);

}

