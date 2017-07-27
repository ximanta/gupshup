package com.stackroute.gupshup.mailboxservice.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.stackroute.gupshup.mailboxservice.exception.MailboxException;
import com.stackroute.gupshup.mailboxservice.model.Mails;
import com.stackroute.gupshup.mailboxservice.service.InboxService;

@RestController
@CrossOrigin
@RequestMapping("/mailbox")
public class MailBoxController {
	
	@Autowired
	private InboxService inboxService;
	
	@Autowired
	private Environment environment;

	//---------------Retrieve inbox for user name---------------
	@RequestMapping(value = "/{circleID}", method = RequestMethod.GET)
	public ResponseEntity getMessages(@PathVariable String circleID, @RequestParam String userName, @RequestParam int page)	{
		RestTemplate restTemplate = new RestTemplate();
		String url = environment.getProperty("mailboxservice.circleservice-address")+"/circle/"+circleID+"/mailbox?userName="+userName+"&page="+page;
		ResponseEntity<Mails[]> entity = restTemplate.getForEntity(url, Mails[].class);
		List<Mails> mcList = new ArrayList<>(Arrays.asList(entity.getBody()));
		System.out.println(userName);
		System.out.println(mcList.size());
		List<Mails> filterMails = new ArrayList<>();
		try {
			filterMails = inboxService.filterMails(mcList, userName);
		} catch (MailboxException e) {
			e.printStackTrace();
			new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		System.out.println(filterMails.size());
		return new ResponseEntity(filterMails, HttpStatus.OK);
	}

	//---------------Retrieve deletedMails by username for a circle----------------
	@RequestMapping(value = "/{circleID}/user/{userName}", method = RequestMethod.GET)
	public ResponseEntity<List<Mails>> getDeletedMails(@PathVariable String circleID,@PathVariable String userName) {
		List<Mails> list = null;
		try {
			list = inboxService.getDeletedMails(userName, circleID);
		} catch (MailboxException e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Mails>>(list, HttpStatus.OK);
	}

	//-----------------------------------Add deleted mail-----------------------------
	@RequestMapping(value = "/{circleID}/user/{userName}", method = RequestMethod.POST)
	public ResponseEntity<Mails> deleteInboxMail(@PathVariable String circleID, @PathVariable String userName, @RequestBody Mails mails )	{
		System.out.println("add: "+ circleID);
		Mails addDeletedMessage = null;
		try {
			addDeletedMessage = inboxService.addDeletedMail(userName, circleID, mails);
		} catch (MailboxException e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Mails>(addDeletedMessage, HttpStatus.OK);
	}   

}