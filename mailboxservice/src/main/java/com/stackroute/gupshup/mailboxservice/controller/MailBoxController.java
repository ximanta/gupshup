package com.stackroute.gupshup.mailboxservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.gupshup.mailboxservice.model.Mails;
import com.stackroute.gupshup.mailboxservice.service.InboxService;

@RestController
@RequestMapping("/mailbox")
public class MailBoxController {

	@Autowired
	private InboxService inboxService;
	
	//---------------------Retrieve inbox for user name---------------
	@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
	public ResponseEntity<List<Mails>> viewMailBox(@PathVariable String userName)
	{
		List<Mails> list=inboxService.viewMailBox(userName);
		return new ResponseEntity<List<Mails>>(list, HttpStatus.FOUND);
	}

	//---------------------Delete mail-----------------------------
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteInboxMail(@RequestBody Mails mail)
	{
		String msg=inboxService.deleteInbox(mail);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}   
}
