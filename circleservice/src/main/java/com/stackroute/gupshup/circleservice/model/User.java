package com.stackroute.gupshup.circleservice.model;

//import javax.validation.constraints.NotNull;

public class User {
	
	//@NotNull(message="user name is required")
	private String userName;
	
	private String profilePicture;
	
	//@NotNull(message="email id is required")
	private String emailId;
	
	public User() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
