package com.stackroute.gupshup.gupshupMBS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.gupshupMBS.model.Mailbox;
import com.stackroute.gupshup.gupshupMBS.model.Mails;

@Service
public class CircleInboxServiceImpl implements CircleInboxService
{
	@Autowired
	InboxService inboxService;



	//------------------------------LEAVE CIRCLE-------------------------------------

	@Override
	public List<Mailbox> leaveCircle(JsonNode node)
	{
		Mails leftUserMail=new Mails();
		Mails notifiedUserMail=new Mails();
		//ObjectMapper mapper = new ObjectMapper();
		JsonNode actor = node.path("actor");
		String leaveUserName = actor.path("name").asText();
		JsonNode target = node.path("target");
		String circleName=target.path("circleName").asText();
		String notifiedUserName=target.path("name").asText();


		List<Mailbox> list=new ArrayList<>();
		if(leaveUserName==null && circleName==null && notifiedUserMail==null)
		{
			System.out.println("name fields are either null or not specified correctly : LEAVE CIRCLE");
		}
		else
		{
			//---------------- user inbox mail who left the group-------------------
			String message= "you have exited from "+circleName;
			leftUserMail.setCircleName(circleName);
			leftUserMail.setFrom(circleName);
			leftUserMail.setTo(leaveUserName);
			leftUserMail.setMessage(message);
			System.out.println("leave method========"+leftUserMail.getCircleName()+","+leftUserMail.getTo()+","+leftUserMail.getMessage());
			Mailbox leftUser=inboxService.updateInbox(leftUserMail);
			list.add(leftUser);

			//----------------notified user------------------------------------------
			String targetMessage= leaveUserName+" has left from  "+circleName;
			notifiedUserMail.setCircleName(circleName);
			notifiedUserMail.setFrom(circleName);
			notifiedUserMail.setTo(notifiedUserName);
			notifiedUserMail.setMessage(targetMessage);
			Mailbox notifiedUser=inboxService.updateInbox(notifiedUserMail);
			list.add(notifiedUser);
		}
		return list;
	}



	//---------------------------------DELETE CIRCLE----------------------------------

	@Override
	public List<Mailbox> deleteCircle(JsonNode node) 
	{
		Mails mailActor=new Mails();
		Mails mailTarget=new Mails();

		JsonNode actor = node.path("actor");
		String userName = actor.path("name").asText();
		JsonNode target = node.path("target");
		String targetName = target.path("name").asText();
		String circleName = target.path("circleName").asText();

		List<Mailbox> list=new ArrayList<Mailbox>();

		if(userName==null && circleName==null && targetName==null)
		{
			System.out.println("name fields are either null or not specified correctly : DELETE CIRCLE");
		}
		else
		{
			//to the the user who is deleting
			mailActor.setTo(userName);
			mailActor.setFrom(circleName);
			String message= "you deleted the circle: "+circleName;
			mailActor.setMessage(message);
			Mailbox m1=inboxService.updateInbox(mailActor);
			list.add(m1);

			//to the user who is a part of circle which will be deleted
			mailTarget.setTo(targetName);
			mailTarget.setFrom(circleName);
			String messageTarget= userName+" deleted the circle: "+circleName;
			mailTarget.setMessage(messageTarget);
			Mailbox m2=inboxService.updateInbox(mailTarget);
			list.add(m2);
		}
		return list;
	}



	//---------------------------------MESSAGE CIRCLE-------------------------------

	@Override
	public Mailbox messageCircle(JsonNode node) 
	{
		Mails mail=new Mails();
		JsonNode actor = node.path("actor");
		String postedUser = actor.path("name").asText();
		JsonNode target = node.path("target");
		String recipientUser = target.path("name").asText();
		JsonNode object = node.path("object");
		String circleName = target.path("circleName").asText();
		String messageContext = object.path("content").asText();
		Mailbox mailBox=null;
		if(postedUser==null && recipientUser==null && circleName ==null && messageContext==null)
		{

			System.out.println("name fields are either null or not specified correctly : MESSAGE CIRCLE");

		}
		else
		{
			//mails to the target users from the posted user
			mail.setCircleName(circleName);
			mail.setTo(recipientUser);
			mail.setFrom(postedUser);
			mail.setMessage(messageContext);
			mailBox=inboxService.updateInbox(mail);
		}
		return mailBox;
	}


	//------------------------------CREATE CIRCLE--------------------------------------

	@Override
	public Mailbox createCircle(JsonNode node) 
	{
		Mails mail=new Mails();

		System.out.println("create Circle method");
		JsonNode actor = node.path("actor");
		String userName = actor.path("name").asText();
		JsonNode target = node.path("target");
		String circleName=target.path("circleName").asText();
		Mailbox mailBox=null;

		if( userName==null && circleName ==null )
		{

			System.out.println("name fields are either null or not specified correctly : MESSAGE CIRCLE");

		}
		else
		{
			//Setting inbox to to user who is creating the circle
			mail.setCircleName(circleName);
			mail.setTo(userName);
			mail.setFrom(userName);
			String message="you have created circle with the name:"+circleName;
			mail.setMessage(message);
			System.out.println("=================Inside create circle method============");
			System.out.println("=================circleName"+mail.getCircleName());
			mailBox=inboxService.updateInbox(mail);
		}
		return mailBox;
	}


	//---------------------------------JOIN CIRCLE------------------------------------

	@Override
	public List<Mailbox> joinCircle(JsonNode node) 
	{
		Mails mailActor=new Mails();
		Mails mailTarget=new Mails();

		JsonNode actor = node.path("actor");
		String userName = actor.path("name").asText();
		JsonNode target = node.path("target");
		String targetName=target.path("name").asText();
		String circleName=target.path("circleName").asText();
		List<Mailbox> list=new ArrayList<Mailbox>();
		Mailbox mailBox1=null;
		Mailbox mailBox2=null;

		if(userName==null && targetName==null && circleName==null)
		{

			System.out.println("name fields are either null or not specified correctly : MESSAGE CIRCLE");

		}
		else
		{
			//---------------------user who is joining-------------------------
			mailActor.setTo(userName);
			// mailActor.setFrom(userName);
			String message= "you have joined to circle "+circleName ;
			mailActor.setMessage(message);
			mailBox1=inboxService.updateInbox(mailActor);
			list.add(mailBox1);


			//---------------------notified user-----------------------------------
			mailTarget.setTo(targetName);
			mailTarget.setFrom(userName);
			String messageTarget= userName +"  has joined  to your circle "+circleName;
			mailTarget.setMessage(messageTarget);
			mailBox2=inboxService.updateInbox(mailTarget);
			list.add( mailBox2);
		}
		return list;
	}


	//-------------------------------UPDATE CIRCLE------------------------------------

	@Override
	public Mailbox updateCircle(JsonNode node) 
	{
		Mails mailActor=new Mails();
		Mailbox mailBox=null;

		//---retrival of data from json node-----------------	     
		JsonNode actor = node.path("actor");
		String updatedUserName = actor.path("name").asText();
		JsonNode object = node.path("object");
		String updatedType=object.path("type").asText();
		JsonNode target = node.path("target");
		String circleName=target.path("circleName").asText();

		if(updatedUserName==null && updatedType==null && circleName==null)
		{
			System.out.println("name fields are either null or not specified correctly : MESSAGE CIRCLE");
		}
		else
		{
			//---- notifying user who has updated -----------
			mailActor.setTo(updatedUserName);
			String message= "you have updated profile"+updatedType+" in "+circleName;
			mailActor.setMessage(message);
			mailBox=inboxService.updateInbox(mailActor);
		}
		return mailBox;	
	}

}

//---------------------------------END----------------------------------------------