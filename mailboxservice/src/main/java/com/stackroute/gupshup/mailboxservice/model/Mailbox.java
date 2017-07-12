package com.stackroute.gupshup.mailboxservice.model;

import java.util.Date;
//import java.util.List;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="deleteMailbox")
public class Mailbox
{
	@Id
	private String mailboxID;
	
	@NotNull(message="username can not be null")
	private String username;
	
	private Date mailboxCreatedDate;
	private long mailCount;
	private List<String> circlelist;
	
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
	public Date getMailboxCreatedDate() {
		return mailboxCreatedDate;
	}
	public void setMailboxCreatedDate(Date mailboxCreatedDate) {
		this.mailboxCreatedDate = mailboxCreatedDate;
	}
	public long getMailCount() {
		return mailCount;
	}
	public void setMailCount(long mailCount) {
		this.mailCount = mailCount;
	}
	public List<String> getCirclelist() {
		return circlelist;
	}
	public void setCirclelist(List<String> circlelist) {
		this.circlelist = circlelist;
	}
}