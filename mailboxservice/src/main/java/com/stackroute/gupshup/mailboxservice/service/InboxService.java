package com.stackroute.gupshup.mailboxservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.mailboxservice.exception.MailboxNotFoundException;
import com.stackroute.gupshup.mailboxservice.model.Mailbox;
import com.stackroute.gupshup.mailboxservice.model.Mails;

public interface InboxService 
{
	
	 //to display a user mailbox
	 public Mailbox viewMailbox(String userName); 
    
	 //Actions related to mailbox
	 public Mailbox createMailbox(String userName);
     public void deleteMailbox(String userName) throws MailboxNotFoundException;
	 public void flushInboxDb();
	 
	 //to  add/delete mails into particular mailbox 
	 public Mailbox addMail(Mails mail); 
	 public Mailbox deleteMail(Mails mail); 
	 
	 //to check activity type
     public void checkActivityType(JsonNode node);
	 
}