package com.stackroute.gupshup.mailboxservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.gupshup.mailboxservice.exception.MailboxNotFoundException;
import com.stackroute.gupshup.mailboxservice.model.Mailbox;
import com.stackroute.gupshup.mailboxservice.model.Mails;
import com.stackroute.gupshup.mailboxservice.service.InboxService;

@RestController
@RequestMapping("/mailbox/")
public class MailBoxController {

	@Autowired
	private InboxService inboxService;

	//	@Autowired
	//	private LinkAssembler linkAssembler;

	//-------------------Retrieve a user mailbox---------------
	@RequestMapping(value = "{username}", method = RequestMethod.GET)
	public ResponseEntity<Mailbox> viewMailBox( @PathVariable String username ) {
		Mailbox mailBox = inboxService.viewMailbox(username);
		return new ResponseEntity<Mailbox>(mailBox, HttpStatus.FOUND);
	}

	//-------------------Create a mailbox to userId- -----------------------------
	@RequestMapping(value="{username}",method=RequestMethod.POST)
	public ResponseEntity<Mailbox> createMailBox(@PathVariable String username) {
		Mailbox mailBox = inboxService.createMailbox(username); 
		return new ResponseEntity<Mailbox>(mailBox, HttpStatus.CREATED);
	}

	//-------------------Delete mailBox by userID-----------------
	@RequestMapping(value="{username}",method=RequestMethod.DELETE)
	public ResponseEntity<Mailbox> deleteMailBox(@PathVariable String username)  
	{
		try {
			inboxService.deleteMailbox(username);
		} catch (MailboxNotFoundException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Mailbox>(HttpStatus.OK);
	}

	//-------------------Add mail to a user mailbox-----------------------------
	@RequestMapping(value = "{username}/mail", method = RequestMethod.POST)
	public ResponseEntity<Mailbox> addInboxMail(@RequestBody Mails mail)
	{
		Mailbox mailBox = inboxService.addMail(mail);
		return new ResponseEntity<Mailbox>(mailBox, HttpStatus.CREATED);
	}    

	//--------------------Delete mail from a user mailbox-----------------------------
	@RequestMapping(value = "/{username}/mail/{mailID}", method = RequestMethod.DELETE)
	public ResponseEntity<Mailbox> deleteInboxMail(@PathVariable String username, @PathVariable String mailID)
	{
		Mails mail = new Mails();
		mail.setMailID(mailID);
		Mailbox mailBox = inboxService.deleteMail(mail);
		return new ResponseEntity<Mailbox>(mailBox, HttpStatus.CREATED);
	}   

}
