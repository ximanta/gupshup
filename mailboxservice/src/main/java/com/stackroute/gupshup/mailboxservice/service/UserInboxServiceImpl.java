package com.stackroute.gupshup.mailboxservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.mailboxservice.model.Mailbox;
import com.stackroute.gupshup.mailboxservice.model.Mails;


@Service
public class UserInboxServiceImpl implements UserInboxService 
{
	@Autowired
	InboxService inboxService;

	//----------------------TO CREATE A USER MAILBOX WITH DEFAULT MESSAGE-------------
	@Override
	public Mailbox createUser(JsonNode node) 
	{
		JsonNode actor = node.path("actor");
		String userName = actor.path("id").asText();
		
		Mails mail=new Mails();
		Mailbox mailbox = inboxService.getMailbox(userName);

		if(mailbox == null) {
			// creating a empty mailbox for a registered user
			mailbox=inboxService.createMailBox(userName);

			// update newly created mailbox with default message
			String message="Mailbox has been successfully created for ::::"+userName;
			mail.setTo(userName);
			mail.setTimeCreated(new Date());
			mail.setMailboxID(mailbox.getMailboxID());
			mail.setCircleName(null);
			mail.setMessage(message);
			inboxService.updateInbox(mail);
		}
		return mailbox;
	}

	//----------------------TO DELETE A USER MAILBOX-----------------------------------
	@Override
	public void deleteUser(JsonNode node) 
	{
		JsonNode actor = node.path("actor");
		String userName = actor.path("name").asText();

		// to check whether user name exists or not
		Mailbox mailbox = inboxService.getMailbox(userName);

		if(mailbox != null) {
			// delete the  mailbox for the particular user
			inboxService.deleteMailBox(userName);
		}
	}

	//-----------------------TO NOTIFY BOTH WHO FOLLOWS AND WHO IS BEING FOLLOWED-----------
	@Override
	public List<Mails> followUser(JsonNode node)
	{
		List<Mails> list = new ArrayList<Mails>();
		Mails actorMail=new Mails();
		Mails targetMail=new Mails();
		
		JsonNode actor = node.path("actor");
		String actorUserName = actor.path("name").asText();
		
		JsonNode target = node.path("target");
		String targetUserName = target.path("name").asText();

		// to check whether user mailbox exists or not
		Mailbox actorMailbox = inboxService.getMailbox(actorUserName);
		Mailbox targetMailbox = inboxService.getMailbox(targetUserName);

		if( actorMailbox != null && targetMailbox != null) {
			// mail for actor
			String mailboxID = actorMailbox.getMailboxID();
			String message= "You started following "+targetUserName;

			actorMail.setMailboxID(mailboxID);
			actorMail.setTo(actorUserName);
			actorMail.setCircleName(null);
			actorMail.setFrom(actorUserName);
			actorMail.setMessage(message);
			actorMail.setTimeCreated(new Date());
			Mails mail1=inboxService.updateInbox(actorMail);
			list.add(mail1);

			//INCREMENTING MAIL COUNT FOR THIS MAILBOX 
			inboxService.incrementMailCount(actorUserName);

			// mail for target
			mailboxID = targetMailbox.getMailboxID();
			message= "You are follwed by "+ actorUserName;
			targetMail.setMailboxID(mailboxID);
			targetMail.setTo(targetUserName);
			targetMail.setFrom(actorUserName);
			targetMail.setMessage(message);
			targetMail.setTimeCreated(new Date());
			targetMail.setCircleName(null);
			Mails mail2=inboxService.updateInbox(targetMail);
			list.add(mail2);

			//INCREMENTING MAIL COUNT FOR THIS MAILBOX 
			inboxService.incrementMailCount(actorUserName);
		}
		return list;
	}
	
	//---------------------TO NOTIFY THE USER ABOUT UPDATION IN USER PROFILE-----------
	//	@Override
	//	public Mailbox updateUser(JsonNode node) 
	//	{
	//		Mails mailActor=new Mails();
	//
	//		JsonNode actor = node.path("actor");
	//		String updatedUserName = actor.path("name").asText();
	//		JsonNode object = node.path("object");
	//		String updatedType=object.path("type").asText();
	//		Mailbox m1=null;
	//		if(updatedUserName==null && updatedType==null)
	//		{
	//			System.out.println("name fields are either null or not specified correctly : UPDATE USER");
	//		} 
	//		else
	//		{
	//			//---- notifying user who has updated -----------
	//			mailActor.setTo(updatedUserName);
	//			String message= "you have updated your profile"+updatedType;
	//			mailActor.setMessage(message);
	//			m1=inboxService.updateInbox(mailActor);
	//		}
	//		return m1;
	//
	//	}

	//-----------------------DIRECT MESSAGING---------------------------------------- 
	//	@Override
	//	public Mailbox directMessage(JsonNode node) 
	//	{
	//		Mails targetMail=new Mails();
	//		JsonNode actor = node.path("actor");
	//		String fromUserName = actor.path("name").asText();
	//		String message=actor.path("content").asText();
	//		JsonNode target = node.path("target");
	//		String toUserName=target.path("name").asText();
	//		Mailbox mailBox=null;
	//
	//		if( fromUserName==null && toUserName==null && message==null )
	//		{
	//			System.out.println("name fields are either null or not specified correctly : DIRECT MESSAGES");
	//		}
	//		else
	//		{
	//			//setting inbox mail to the recipient
	//			targetMail.setTo(toUserName);
	//			targetMail.setFrom(fromUserName);
	//			targetMail.setMessage(message);
	//			mailBox=inboxService.updateInbox(targetMail);
	//		}
	//		return mailBox;
	//	}
	//
}
