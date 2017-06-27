package com.stackroute.gupshup.mailboxservice.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

@Document(collection="mailBox")
public class Mailbox extends ResourceSupport {
	
	@Id
	private String mailboxID;
	
	@NotNull(message="username can not be null")
	private String username;
	
	private List<Mails> inbox;
	private List<Mails> outbox;

	public String getMailboxID() {
		return mailboxID;
	}
	
	public void setMailboxID(String mailboxID) {
		this.mailboxID = mailboxID;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
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