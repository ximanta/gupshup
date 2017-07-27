package com.stackroute.gupshup.mailboxservice.service;

import java.util.List;

import com.stackroute.gupshup.mailboxservice.exception.MailboxException;
import com.stackroute.gupshup.mailboxservice.model.Mailbox;
import com.stackroute.gupshup.mailboxservice.model.Mails;

public interface InboxService {
	
	public void consumeActivity(String activity);
	public Mailbox createMailbox(String userName) throws MailboxException;
	public Mails addDeletedMail(String userName, String circleId, Mails mails) throws MailboxException;
	public List<Mails> getDeletedMails(String userName, String circleID) throws MailboxException;
	public List<Mails> filterMails(List<Mails> mailslist, String username) throws MailboxException;
	
}