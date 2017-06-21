package com.stackroute.gupshup.circleservice.model;


import java.util.Date;

public class Mail
{
	long mailID;
	String To;
    String From;
    String message;
    Date timeCreated;
    
	public Date getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
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
	public long getMailID() {
		return mailID;
	}
	public void setMailID(long mailID) {
		this.mailID = mailID;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
}