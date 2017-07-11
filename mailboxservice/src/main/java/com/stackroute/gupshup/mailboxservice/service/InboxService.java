package com.stackroute.gupshup.mailboxservice.service;

import java.util.List;

import com.stackroute.gupshup.mailboxservice.exception.MailboxException;
import com.stackroute.gupshup.mailboxservice.model.Mailbox;
import com.stackroute.gupshup.mailboxservice.model.Mails;
import com.stackroute.gupshup.mailboxservice.model.Message;

public interface InboxService {
	
	public void consumeActivity(String activity);
	public Mailbox createMailbox(String userName) throws MailboxException;
	public Mailbox addCircle( String userName, String circleID) throws MailboxException;
	public Mails addDeletedMessage(String userName, String circleID, String mailID) throws MailboxException;
	public List<Message> getMessages(String username, String circleID, int page);
	public List<Mails> getDeletedMails(String userName, String circleID);
	
}