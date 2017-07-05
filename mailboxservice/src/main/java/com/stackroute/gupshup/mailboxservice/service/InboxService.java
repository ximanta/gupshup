package com.stackroute.gupshup.mailboxservice.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import com.stackroute.gupshup.mailboxservice.model.Mailbox;
import com.stackroute.gupshup.mailboxservice.model.Mails;



public interface InboxService 
{
	//to display all mailbox
	public List<Mails> viewMailBox(String userName); 

	//Actions related to mailbox
	public Mailbox createMailBox(String userName);
	public void deleteMailBox(String userName);

	//to  add/delete mails into particular mailbox 
	public Mails updateInbox(Mails mail); 
	public String deleteInbox(Mails mail);

	//to check activity type
	public void checkActivityType(String activity, int partitionID);
	
	public String checkUserName(String userName);
	public String getMailboxID(String userName);
	public void incrementMailCount(String userName);
	public Mailbox getMailbox(String userName);
	public void decrementMailCount(String userName);




}