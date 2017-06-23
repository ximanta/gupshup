package com.stackroute.gupshup.gupshupMBS.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.gupshupMBS.model.Mailbox;

public interface CircleInboxService 
{
	public List<Mailbox> leaveCircle(JsonNode node);
    public List<Mailbox> deleteCircle(JsonNode node);
	public Mailbox messageCircle(JsonNode node);
	public Mailbox createCircle(JsonNode node);
	public List<Mailbox> joinCircle(JsonNode node);
	public Mailbox updateCircle(JsonNode node);
}
