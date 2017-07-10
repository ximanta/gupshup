package com.stackroute.gupshup.mailboxservice.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.gupshup.mailboxservice.exception.MailboxException;
import com.stackroute.gupshup.mailboxservice.model.Mails;
import com.stackroute.gupshup.mailboxservice.model.Message;
import com.stackroute.gupshup.mailboxservice.service.InboxService;

@RestController
@RequestMapping("/mailbox")
public class MailBoxController 
{
	@Autowired
	private InboxService inboxService;


	//---------------Retrieve inbox for user name---------------
	@RequestMapping(value = "/{circleID}/{userName}", method = RequestMethod.GET)
	public ResponseEntity<List<Message>> getMessages(@PathVariable String circleID,@PathVariable String userName)
	{
		List<Message> messages = inboxService.getMessages(circleID, userName);
		return new ResponseEntity<List<Message>>(messages, HttpStatus.FOUND);
	}

	//---------------Retrieve inbox for user name---------------
	@RequestMapping(value = "/delete/{circleID}/{userName}", method = RequestMethod.GET)
	public ResponseEntity<List<Mails>> getDeletedMails(@PathVariable String circleID,@PathVariable String userName)
	{
		List<Mails> messages = inboxService.getDeletedMails(userName, circleID);
		System.out.println(messages);
		return new ResponseEntity<List<Mails>>(messages, HttpStatus.FOUND);
	}

	//----------------Add deleted mail-----------------------------
	@RequestMapping(value = "/{circleID}/{userName}/{mailID}", method = RequestMethod.POST)
	public ResponseEntity<Mails> deleteInboxMail(@PathVariable String circleID,@PathVariable String userName,@PathVariable String mailID)
	{

		Mails addDeletedMessage = null;
		try {
			addDeletedMessage = inboxService.addDeletedMessage(userName, circleID, mailID);
		} catch (MailboxException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Mails>(addDeletedMessage, HttpStatus.OK);
	}   




}