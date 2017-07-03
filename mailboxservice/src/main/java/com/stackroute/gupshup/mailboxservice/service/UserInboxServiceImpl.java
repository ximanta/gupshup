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

		System.out.println("ENTERED USER-INBOX:CREATE USER :::");
		Mails mail=new Mails();
		Mailbox mailbox = null;
		JsonNode actor = node.path("actor");
		String userName = actor.path("name").asText();

		//to check whether user name exists or not
		String userName1=inboxService.checkUserName(userName);
		System.out.println("------"+userName);

		if(userName1!=null)
		{
			System.out.println("Name Field is empty or Name already exists");
		}

		else
		{
			System.out.println("before");	
			//creating a empty mailbox for a registered user
			mailbox=inboxService.createMailBoxService(userName);
			System.out.println("after");

			//update newly created mailbox with default message
			String message="Mailbox has been successfully created for ::::"+userName;
			mail.setTo(userName);
			mail.setTimeCreated(new Date());
			mail.setMailboxID(mailbox.getMailboxID());
			mail.setCircleName(null);
			mail.setMessage(message);
			System.out.println("NEW MAIL HAS BEEN CREATED FOR REGISTERED USER :: USER NAME : "+mail.getTo());
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

		//to check whether user name exists or not
		String userName1=inboxService.checkUserName(userName);
		System.out.println("----------"+userName1);

		if(userName1==null)
		{
			System.out.println("Name Field is empty or User Name doesnot exist");
		}
		else
		{
			//Delete the  mailbox for the particular user
			inboxService.deleteMailBoxService(userName);
		}

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


	//--------------------TO NOTIFY BOTH WHO FOLLOWS AND WHO IS BEING FOLLOWED-----------

	@Override
	public List<Mails> followUser(JsonNode node)
	{
		Mails mailActor=new Mails();
		Mails mailTarget=new Mails();

		System.out.println("ENTERING USER-INBOX SERVICE:FOLLOW USER");
		JsonNode actor = node.path("actor");
		String followerUserName = actor.path("name").asText();
		JsonNode target = node.path("target");
		String targetUserName = target.path("name").asText();

		List<Mails> list=new ArrayList<Mails>();

		//to check whether user name exists or not
		String followerUserName1=inboxService.checkUserName(followerUserName);
		String targetUserName1=inboxService.checkUserName(targetUserName);
		System.out.println(" FOLLOWER : "+followerUserName+" TARGET : "+targetUserName);
		System.out.println(" FOLLOWER1 : "+followerUserName1+" TARGET1 : "+targetUserName1);

		if( followerUserName1==null || targetUserName1==null )
		{
			System.out.println("SORRY!!! NAME FIELDS ARE EITHER EMPTY or USER NAME DOESNOT EXIST ::: {FOLLOW USER METHOD}");
		}
		else
		{
			//FOLLOWED USER


				String mailboxID=inboxService.getMailboxID(targetUserName);
				String message= followerUserName+" is following you";
				System.out.println(followerUserName+" is following you.....");
	
				mailActor.setMailboxID(mailboxID);
				mailActor.setTo(targetUserName);
				mailActor.setCircleName(null);
				mailActor.setFrom(followerUserName);
				mailActor.setMessage(message);
				mailActor.setTimeCreated(new Date());
				Mails mail1=inboxService.updateInbox(mailActor);
				list.add(mail1);
	
				//INCREMENTING MAIL COUNT FOR THIS MAILBOX 
				inboxService.incrementMailCount(targetUserName);

			//FOLLOWING USER

				mailboxID=inboxService.getMailboxID(followerUserName);
				message= "you are following "+targetUserName;
				System.out.println("you are following "+targetUserName);
				mailTarget.setMailboxID(mailboxID);
				mailTarget.setTo(followerUserName);
				mailTarget.setFrom(targetUserName);
				mailTarget.setMessage(message);
				mailTarget.setTimeCreated(new Date());
				mailTarget.setCircleName(null);
				Mails mail2=inboxService.updateInbox(mailTarget);
				list.add(mail2);
	
				//INCREMENTING MAIL COUNT FOR THIS MAILBOX 
				inboxService.incrementMailCount(followerUserName);
		}
		return list;
	}





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

//-----------------------------------END----------------------------------------------