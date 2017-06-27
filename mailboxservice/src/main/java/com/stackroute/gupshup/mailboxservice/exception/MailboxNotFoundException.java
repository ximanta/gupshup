package com.stackroute.gupshup.mailboxservice.exception;

public class MailboxNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public MailboxNotFoundException( String message ) {
		super(message);
	}
}
