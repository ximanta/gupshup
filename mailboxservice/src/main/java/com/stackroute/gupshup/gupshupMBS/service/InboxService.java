package com.stackroute.gupshup.gupshupMBS.service;



import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.gupshupMBS.model.Mailbox;
import com.stackroute.gupshup.gupshupMBS.model.Mails;


public interface InboxService 
{
	 //to display all mailbox
	 public Mailbox viewMailBoxService(String userName); 
    
	 //Actions related to mailbox
	 public Mailbox createMailBoxService(String userName);
     public void deleteMailBoxService(String userName);
	 public void flushInboxDb();
	 
	 //to  add/delete mails into particular mailbox 
	 public Mailbox updateInbox(Mails mail); 
	 public Mailbox deleteInbox(Mails mail); 
	 
	 //to check activity type
     public void checkActivityType(JsonNode node);

   

	 
}