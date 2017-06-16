package com.stackroute.gupshup.userservice.exception;

public class UserCreateException extends Exception {
	
	/*public UserCreationException(String message)
	{
		super(message);
	}
*/
	
	@Override
	public String toString()
	{
		return "Failed to register";
	}
}
