package com.stackroute.gupshup.userservice.exception;

public class GetUserException extends Exception {

	@Override
	public String toString()
	{
		return "User not found";
	}
}
