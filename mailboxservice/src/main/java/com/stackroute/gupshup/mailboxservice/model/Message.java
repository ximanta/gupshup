package com.stackroute.gupshup.mailboxservice.model;


import java.util.Date;


public class Message
{
	private String mailID;
	private String To;
	private String From;
	private String message;
	private Date   timeCreated;
	private String circleID;


	

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

	public String getTo() {
		return To;
	}

	public void setTo(String to) {
		To = to;
	}

	public String getFrom() {
		return From;
	}

	public void setFrom(String from) {
		From = from;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}
	
}