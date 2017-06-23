package com.stackroute.gupshup.gupshupMBS.model;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

@Document(collection="mailBox")
public class Mailbox extends ResourceSupport
{
	
	@Id
	String mailboxID; 
	String userName;
	List<Mails> inbox;
	List<Mails> outbox;

	
	public String getMailboxID() {
		return mailboxID;
	}
	public void setMailboxID(String mailboxID) {
		this.mailboxID = mailboxID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<Mails> getInbox() {
		return inbox;
	}
	public void setInbox(List<Mails> inbox) {
		this.inbox = inbox;
	}
	public List<Mails> getOutbox() {
		return outbox;
	}
	public void setOutbox(List<Mails> outbox) {
		this.outbox = outbox;
	}
}