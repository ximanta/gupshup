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
public class CircleInboxServiceImpl implements CircleInboxService
{
	@Autowired
	InboxService inboxService;

	//------------------------------LEAVE CIRCLE-------------------------------------
	@Override
	public List<Mails> leaveCircle(JsonNode node) {
		Mails leftUserMail=new Mails();
		Mails notifiedUserMail=new Mails();
		List<Mails> list=new ArrayList<>();

		JsonNode actor = node.path("actor");
		String leaveUserName = actor.path("name").asText();
		JsonNode target = node.path("target");
		String circleName=target.path("circlename").asText();
		String notifiedUserName=target.path("name").asText();

		//to check whether user name exists or not
		String leaveUserName1=inboxService.checkUserName(leaveUserName);
		String notifiedUserName1=inboxService.checkUserName(notifiedUserName);

		System.out.println("LEAVE USERNAME1 : "+leaveUserName1+"  LEAVE USERNAME : "+leaveUserName+"  NOTIFIED NAME1 : "+notifiedUserName1+" NOTIFIED NAME : "+notifiedUserName+"  CIRCLE NAME : "+circleName);
		if(leaveUserName1==null || notifiedUserName1==null || circleName==null || circleName=="")
		{
			System.out.println("SORRY!!! NAME FIELDS ARE EITHER EMPTY or USER NAME DOESNOT EXIST OR CIRCLE NAME IS NOT PROVIDED ::: LEAVE CIRCLE");
		}
		else
		{

			//LEFT USER INBOX

			String message= "You left from "+circleName;
			String mailboxID=inboxService.getMailboxID(leaveUserName);

			leftUserMail.setMailboxID(mailboxID);
			leftUserMail.setCircleName(circleName);
			leftUserMail.setFrom(circleName);
			leftUserMail.setTo(leaveUserName);
			leftUserMail.setMessage(message);
			leftUserMail.setTimeCreated(new Date());

			System.out.println("You left from "+circleName);

			Mails mail1=inboxService.updateInbox(leftUserMail);
			list.add(mail1);

			//INCREMENTING MAIL COUNT FOR THIS MAILBOX 
			inboxService.incrementMailCount(leaveUserName);

			//NOTIFIED USER INBOX

			message= leaveUserName+" has left from  "+circleName+" group.";
			mailboxID=inboxService.getMailboxID(notifiedUserName);

			notifiedUserMail.setCircleName(circleName);
			notifiedUserMail.setFrom(circleName);
			notifiedUserMail.setTo(notifiedUserName);
			notifiedUserMail.setMessage(message);
			notifiedUserMail.setMailboxID(mailboxID);
			notifiedUserMail.setTimeCreated(new Date());

			System.out.println(leaveUserName+" has left from  "+circleName);

			Mails mail2=inboxService.updateInbox(notifiedUserMail);
			list.add(mail2);

			//INCREMENTING MAIL COUNT FOR THIS MAILBOX 
			inboxService.incrementMailCount(notifiedUserName);
		}
		return list;
	}



	//---------------------------------DELETE CIRCLE----------------------------------
	@Override
	public List<Mails> deleteCircle(JsonNode node) 
	{
		System.out.println("CIRCLE SERVICE ::: DELETE CIRCLE");

		Mails mailActor=new Mails();
		Mails mailTarget=new Mails();

		JsonNode actor = node.path("actor");
		String userName = actor.path("name").asText();
		JsonNode target = node.path("target");
		String targetName = target.path("name").asText();
		String circleName = target.path("circlename").asText();

		List<Mails> list=new ArrayList<Mails>();

		//to check whether user name exists or not
		String userName1=inboxService.checkUserName(userName);
		String targetName1=inboxService.checkUserName(targetName);

		System.out.println("USER NAME1 : "+userName1+"  USER NAME : "+userName+" TARGET NAME 1 : "+targetName1+" TARGET NAME : "+targetName+" CIRCLE NAME : "+circleName);
		if(userName1==null || targetName1==null || circleName==null || circleName=="")
		{
			System.out.println("SORRY!!! NAME FIELDS ARE EITHER EMPTY or USER NAME DOESNOT EXIST OR CIRCLE NAME IS NOT PROVIDED ::: DELETE CIRCLE");
		}
		else
		{

			//CIRCLE DELETING USER INBOX

			String message= " you deleted the circle : "+circleName;
			String mailboxID=inboxService.getMailboxID(userName);

			mailActor.setMailboxID(mailboxID);
			mailActor.setTo(userName);
			mailActor.setFrom(circleName);
			mailActor.setMessage(message);
			mailActor.setCircleName(circleName);
			mailActor.setTimeCreated(new Date());

			Mails m1=inboxService.updateInbox(mailActor);
			list.add(m1);

			//INCREMENTING MAIL COUNT FOR THIS MAILBOX 
			inboxService.incrementMailCount(userName);

			//NOTIFIED USER INBOX

			message= userName+" deleted the circle : "+circleName;
			mailboxID=inboxService.getMailboxID(targetName);

			mailTarget.setTo(targetName);
			mailTarget.setFrom(circleName);
			mailTarget.setMessage(message);
			mailTarget.setCircleName(circleName);
			mailTarget.setMailboxID(mailboxID);
			mailTarget.setTimeCreated(new Date());

			Mails m2=inboxService.updateInbox(mailTarget);
			list.add(m2);

			//INCREMENTING MAIL COUNT FOR THIS MAILBOX 
			inboxService.incrementMailCount(targetName);
		}
		return list;
	}



	//---------------------------------MESSAGE CIRCLE-------------------------------

	@Override
	public Mails messageCircle(JsonNode node) 
	{
		System.out.println("CIRCLE SERVICE:::MESSAGE TO CIRCLE");

		Mails mail=new Mails();
		Mails mail1=null;

		JsonNode actor = node.path("actor");
		String sender = actor.path("id").asText();

		JsonNode target = node.path("target");
		String receiver = target.path("id").asText();

		JsonNode object = node.path("object");
		//		String circleName = target.path("circleName").asText();
		String messageContent = object.path("content").asText();

		// to check whether user name exists or not
		Mailbox senderMailbox = inboxService.getMailbox(sender);
		Mailbox receiverMailbox = inboxService.getMailbox(receiver);

		if(receiverMailbox != null && senderMailbox !=null) {

			//  RECIPIENT USER INBOX
			String  mailboxID=receiverMailbox.getMailboxID();

			mail.setMailboxID(mailboxID);
			mail.setTimeCreated(new Date());
			mail.setCircleName(null);
			mail.setTo(receiver);
			mail.setFrom(sender);
			mail.setMessage(messageContent);

			mail1=inboxService.updateInbox(mail);

			//  INCREMENTING MAIL COUNT FOR THIS MAILBOX 
			inboxService.incrementMailCount(receiver);
		}
		return mail1;
	}

	//------------------------------CREATE CIRCLE--------------------------------------
	@Override
	public Mails createCircle(JsonNode node) 
	{
		Mails mail=new Mails();

		JsonNode actor = node.path("actor");
		String userID = actor.path("id").asText();
		
		JsonNode target = node.path("object");
		String circleName = target.path("name").asText();

		// to check whether user mailbox exists or not
		Mailbox mailbox = inboxService.getMailbox(userID);

		if( mailbox != null) {

			String mailboxID=mailbox.getMailboxID();
			String message="You have created circle with the name :::  "+circleName;
			mail.setMailboxID(mailboxID);
			mail.setCircleName(circleName);
			mail.setTo(userID);
			mail.setFrom(circleName);
			mail.setMessage(message);
			mail.setTimeCreated(new Date());
			inboxService.updateInbox(mail);

			//INCREMENTING MAIL COUNT FOR THIS MAILBOX 
			inboxService.incrementMailCount(userID);
		}
		return mail;
	}

	//---------------------------------JOIN CIRCLE------------------------------------
	@Override
	public List<Mails> joinCircle(JsonNode node) 
	{
		Mails mail=new Mails();
		
		JsonNode actor = node.path("actor");
		String userName = actor.path("id").asText();
		
		JsonNode target = node.path("object");
		String circleName=target.path("id").asText();
		
		String message = node.path("summary").asText();
				
		List<Mails> list = new ArrayList<Mails>();

		// to check whether user mailbox exists or not
		Mailbox mailbox = inboxService.getMailbox(userName);

		if( mailbox != null) {

			String mailboxID = mailbox.getMailboxID();

			mail.setTo(userName);
			mail.setFrom(circleName);
			mail.setMessage(message);
			mail.setCircleName(circleName);
			mail.setTimeCreated(new Date());
			mail.setMailboxID(mailboxID);
			
			inboxService.updateInbox(mail);

			//INCREMENTING MAIL COUNT FOR THIS MAILBOX 
			inboxService.incrementMailCount(userName);
		}
		return list;
	}


	//-------------------------------UPDATE CIRCLE------------------------------------
	//
	//	@Override
	//	public Mailbox updateCircle(JsonNode node) 
	//	{
	//		Mails mailActor=new Mails();
	//		Mailbox mailBox=null;
	//
	//		//---retrival of data from json node-----------------	     
	//		JsonNode actor = node.path("actor");
	//		String updatedUserName = actor.path("name").asText();
	//		JsonNode object = node.path("object");
	//		String updatedType=object.path("type").asText();
	//		JsonNode target = node.path("target");
	//		String circleName=target.path("circleName").asText();
	//
	//		if(updatedUserName==null && updatedType==null && circleName==null)
	//		{
	//			System.out.println("name fields are either null or not specified correctly : MESSAGE CIRCLE");
	//		}
	//		else
	//		{
	//			//---- notifying user who has updated -----------
	//			mailActor.setTo(updatedUserName);
	//			String message= "you have updated profile"+updatedType+" in "+circleName;
	//			mailActor.setMessage(message);
	//			mailBox=inboxService.updateInbox(mailActor);
	//		}
	//		return mailBox;	
	//	}
}
