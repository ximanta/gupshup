package com.stackroute.gupshup.mailboxservice.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="deletedMails")
public class Mails {
	
	@Id
	private String mailID;
	private String userName;
	private String circleID;
	private Date deletedDate;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCircleID() {
		return circleID; 
	}
	public void setCircleID(String circleID) {
		this.circleID = circleID;
	}
	public String getMailID() {
		return mailID;
	}
	public void setMailID(String mailID) {
		this.mailID = mailID;
	}
	public Date getDeletedDate() {
		return deletedDate;
	}
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}
}
