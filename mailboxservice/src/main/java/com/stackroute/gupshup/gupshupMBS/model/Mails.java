package com.stackroute.gupshup.gupshupMBS.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@JsonSerialize
public class Mails
{

	@JsonProperty("To")
	String To;
	@JsonProperty("From")
    String From;
	@JsonProperty("message")
    String message;
	@JsonProperty("circleName")
    String circleName;
   // Date timeCreated;
    
    
    public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
//	public Date getTimeCreated() {
//		return timeCreated;
//	}
//	public void setTimeCreated(Date timeCreated) {
//		this.timeCreated = timeCreated;
//	}
	
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
    
}