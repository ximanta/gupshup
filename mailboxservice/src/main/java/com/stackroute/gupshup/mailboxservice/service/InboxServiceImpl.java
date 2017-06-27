package com.stackroute.gupshup.mailboxservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.mailboxservice.exception.MailboxNotFoundException;
import com.stackroute.gupshup.mailboxservice.model.Mailbox;
import com.stackroute.gupshup.mailboxservice.model.Mails;
import com.stackroute.gupshup.mailboxservice.repository.MailboxRepository;

@Service
public class InboxServiceImpl implements InboxService
{

	@Autowired
	MailboxRepository mailboxRepository;

	//--------------------------------TO VIEW USER MAILBOX---------------------------
	@Override
	public Mailbox viewMailbox(String username) {
		Mailbox userMailbox=null;

		for(Mailbox mailbox: mailboxRepository.findAll()) {
			String userName = mailbox.getUsername();
			if(userName.equals(username)) {
				userMailbox = mailbox;
			}
		}
		return userMailbox;
	}

	//--------------------------------TO CREATE USER MAILBOX---------------------------
	@Override
	public Mailbox createMailbox(String username) {
		Mailbox mailBox = viewMailbox(username);
		if(mailBox == null) {
			mailBox = new Mailbox();
			mailBox.setUsername(username);
			mailBox.setInbox(new ArrayList<>());
			mailBox.setOutbox(new ArrayList<>());
		}
		return mailboxRepository.save(mailBox);
	}

	//--------------------------------TO DELETE USER MAILBOX---------------------------
	@Override
	public void deleteMailbox(String username) throws MailboxNotFoundException {
		Mailbox mailbox = viewMailbox(username);
		if(mailbox == null ) {
			throw new MailboxNotFoundException("User Mailbox not found");
		} else {
			mailboxRepository.delete(mailbox);
		}
	}

	//--------------------------------TO DELETE ALL MAILBOXES---------------------------
	@Override
	public void flushInboxDb() {
		mailboxRepository.deleteAll();
	}


	//--------------------------------ADDING/UPDATING INBOX MAILS---------------------------------
	@Override
	public Mailbox addMail(Mails mail)
	{
		String username = mail.getTo();
		Mailbox mailBox = viewMailbox(username);
		System.out.println(mailBox);
        if(mailBox!=null)
        {
		List<Mails> list = mailBox.getInbox();
		list.add(mail);
		mailBox.setInbox(list);
	    mailBox=mailboxRepository.save(mailBox);
        }
		return mailBox;
 
        	
	}

	//--------------------------------DELETING INBOX MAILS---------------------------
	@Override
	public Mailbox deleteMail(Mails mail) 
	{
		String username = mail.getTo();
		Mailbox mailBox = viewMailbox(username);

		List<Mails> mails=mailBox.getInbox();
		for(Mails mailx:mails) {
			if(mailx.getMailID().equals(mail.getMailID())) {
				mails.remove(mailx);
				break;
			}
		}
		mailBox.setInbox(mails);
		return mailboxRepository.save(mailBox);
	}

	//--------------------------------TO CHECK TYPE OF ACTIVITY---------------------------	

	@Override
	public void checkActivityType(JsonNode node) 
	{
		String activityType = node.path("type").asText();

		if(activityType.equalsIgnoreCase("Create"))
		{
			JsonNode actor = node.path("actor");
			String username = actor.path("name").asText();
			
			createMailbox(username);
		}
		
		if(activityType.equalsIgnoreCase("Delete")) 
		{
			JsonNode actor = node.path("actor");
			String username = actor.path("name").asText();
			
			try {
				deleteMailbox(username);
			} catch (MailboxNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if(activityType.equalsIgnoreCase("Add")) 
		{
			JsonNode actor = node.path("actor");
			String from = actor.path("name").asText();
			
			JsonNode target = node.path("target");
			String to  = target.path("name").asText();
			
			JsonNode object = node.path("object");
			String message = object.path("content").asText();
			String circleName = object.path("circleName").asText();
			
			Mails mails = new Mails();
			mails.setTo(to);
			mails.setFrom(from);
			mails.setMessage(message);
			mails.setTimeCreated(new Date());
			mails.setCircleName(circleName);
			mails.setMailID(to+new Date());
			
			addMail(mails);
		}

//		if(activityType.equalsIgnoreCase("Update")) {
//			JsonNode object = node.path("object");
//			String objectType = object.path("type").asText();
//			
//			if(objectType.equals("Circle"))
//				circleInboxService.updateCircle(node);
//			else
//				userInboxService.updateUser(node);
//		}

	}
}
