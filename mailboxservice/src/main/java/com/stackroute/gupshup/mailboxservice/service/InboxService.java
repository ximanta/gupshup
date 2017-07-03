package com.stackroute.gupshup.mailboxservice.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import com.stackroute.gupshup.mailboxservice.model.Mailbox;
import com.stackroute.gupshup.mailboxservice.model.Mails;



public interface InboxService 
{
	//to display all mailbox
	public List<Mails> viewMailBoxService(String userName); 

	//Actions related to mailbox
	public Mailbox createMailBoxService(String userName);
	public void deleteMailBoxService(String userName);
	public void flushInboxDb();

	//to  add/delete mails into particular mailbox 
	public Mails updateInbox(Mails mail); 
	public String deleteInbox(Mails mail);//------------>REST<--------------- 

	//to check activity type
	public void checkActivityType(JsonNode node);

	public String checkUserName(String userName);

	public String getMailboxID(String userName);

	public void incrementMailCount(String userName);

	public Mailbox getMailbox(String userName);

	public void decrementMailCount(String userName);




}