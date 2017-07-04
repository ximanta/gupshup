package com.stackroute.gupshup.circleservice.model;

import javax.validation.constraints.NotNull;

public class User {
	
	@NotNull(message="user name is required")
	private String username;
	
	private String fullname;
	private String profilePicture;
	
	public User() {
	}

	public String getUsername() {
		return username;
	}

	public String getFullname() {
		return fullname;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	
	

}
