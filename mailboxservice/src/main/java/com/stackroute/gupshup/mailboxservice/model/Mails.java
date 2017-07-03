package com.stackroute.gupshup.mailboxservice.model;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="mail")
public class Mails
{
	@Id
	private String mailID;
	private String mailboxID;
	private String to;
    private String from;
    private String message;
    private String circleName;
	private Date timeCreated;
	
	
	public String getMailboxID() {
		return mailboxID;
	}

	public void setMailboxID(String mailboxID) {
		this.mailboxID = mailboxID;
	}
	public String getMailID() {
		return mailID;
	}

	public void setMailID(String mailID) {
		this.mailID = mailID;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}
    
}