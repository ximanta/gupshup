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
	
//	@Autowired
//	private LinkAssembler linkAssembler;

	
	//---------------Retrieve inbox for user name---------------
	@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
	public ResponseEntity<List<Mails>> viewMailBox(@PathVariable String userName)
	{
		List<Mails> list=inboxService.viewMailBoxService(userName);
		return new ResponseEntity<List<Mails>>(list, HttpStatus.FOUND);
	}

	//---------Create a mailbox to userId- -----------------------------
//	@RequestMapping(value="/{userID}",method=RequestMethod.POST)
//	public ResponseEntity createMailBox(@PathVariable String userID) 
//	{
//		Mailbox mailBox = null;
//		mailBox=inboxService.createMailBoxService(userID); 
//		if(mailBox==null)
//		{
//		    return  new ResponseEntity("already found",HttpStatus.CREATED);
//		}
//		return new ResponseEntity(mailBox, HttpStatus.CREATED);
//	}
//

	//--------delete mailBox by userID-----------------
//	@RequestMapping(value="/{userID}",method=RequestMethod.DELETE)
//	public ResponseEntity<Mailbox> deleteMailBox(@PathVariable String userID)  
//	{
//
//		inboxService.deleteMailBoxService(userID);
//		return new ResponseEntity<Mailbox>(HttpStatus.OK);
//
//	}

	// -------------------Updating mails for a perticular user id--------------------------------------------------------

	//-------------------1. adding mail to a userID-----------------------------
//	@RequestMapping(value = "/add", method = RequestMethod.PUT)
//	public ResponseEntity<Mailbox> addInboxMail(@RequestBody Mails mail)
//	{
//		Mailbox mailBox = null;	
//		mailBox=inboxService.updateInbox(mail);
//		return new ResponseEntity<Mailbox>(mailBox, HttpStatus.CREATED);
//	}    

	//-------------------1. delete mail-----------------------------
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteInboxMail(@RequestBody Mails mail)
	{
        System.out.println("hi skdnfvkewjvfnmsw vjfdsvldsa,lcnewrjnvswvckmvdemdvm");
		String msg=inboxService.deleteInbox(mail);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}   



	//	    //-----------------Delete all user------------------
//	@RequestMapping(value = "/deleteall", method = RequestMethod.DELETE)
//	public ResponseEntity<Mailbox> deleteAllCircle() {
//
//		inboxService.flushInboxDb();
//		return new ResponseEntity<Mailbox>(HttpStatus.OK);
//	}
}
