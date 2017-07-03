package com.stackroute.gupshup.mailboxservice.service;



import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.mailboxservice.model.Mails;


public interface CircleInboxService 
{
	public List<Mails> leaveCircle(JsonNode node);
    public List<Mails> deleteCircle(JsonNode node);
	public Mails messageCircle(JsonNode node);
	public Mails createCircle(JsonNode node);
	public List<Mails> joinCircle(JsonNode node);
	//public Mailbox updateCircle(JsonNode node);
}
