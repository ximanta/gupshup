package com.stackroute.gupshup.mailboxservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.mailboxservice.model.Mails;


@Service
public class CircleInboxServiceImpl implements CircleInboxService
{
	
	@Autowired
	InboxService inboxService;



	//------------------------------LEAVE CIRCLE-------------------------------------

	@Override
	public List<Mails> leaveCircle(JsonNode node)
	{
		
		System.out.println("CIRCLE-INBOX SERVICE ::: LEAVE CIRCLE");

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
		String postedUser = actor.path("name").asText();
		JsonNode target = node.path("target");
		String recipientUser = target.path("name").asText();
		JsonNode object = node.path("object");
		String circleName = target.path("circleName").asText();
		String messageContext = object.path("content").asText();


		//to check whether user name exists or not
		String postedUser1=inboxService.checkUserName(postedUser);
		String recipientUser1=inboxService.checkUserName(recipientUser);

		System.out.println("USER NAME1 : "+postedUser1+"  USER NAME : "+postedUser+" TARGET NAME 1 : "+recipientUser1+" TARGET NAME : "+recipientUser+" CIRCLE NAME : "+circleName);
		if(postedUser1==null || recipientUser1==null || circleName==null || circleName=="")
		{
			System.out.println("SORRY!!! NAME FIELDS ARE EITHER EMPTY or USER NAME DOESNOT EXIST OR CIRCLE NAME IS NOT PROVIDED ::: MESSAGE TO CIRCLE");
		}
		else
		{

			//RECIPIENT USER INBOX
					String  mailboxID=inboxService.getMailboxID(recipientUser);
		
					mail.setMailboxID(mailboxID);
					mail.setTimeCreated(new Date());
					mail.setCircleName(circleName);
					mail.setTo(recipientUser);
					mail.setFrom(postedUser);
					mail.setMessage(messageContext);
		
					mail1=inboxService.updateInbox(mail);
					
					//INCREMENTING MAIL COUNT FOR THIS MAILBOX 
					inboxService.incrementMailCount(recipientUser);
		}
		return mail1;
	}


	//------------------------------CREATE CIRCLE--------------------------------------

	@Override
	public Mails createCircle(JsonNode node) 
	{
		System.out.println("CIRCLE SERVICE ::: CREATE CIRCLE METHOD");
		Mails mail=new Mails();
		Mails mail1=null;

		JsonNode actor = node.path("actor");
		String userName = actor.path("name").asText();
		JsonNode target = node.path("target");
		String circleName=target.path("circlename").asText();

		//to check whether user name exists or not
		 String userName1=inboxService.checkUserName(userName);
         System.out.println("USER NAME :"+userName+"----USER NAME 1 :"+userName1+"-----CIRCLE NAME :"+circleName);
		
        if( userName1==null || circleName==null || circleName=="")
		{
			System.out.println("SORRY!!! NAME FIELDS ARE EITHER EMPTY or USER NAME DOESNOT EXIST OR CIRCLE NAME IS NOT PROVIDED::: CREATE CIRCLE");
		}
        else
		{

			//CIRCLE CREATED USER'S INBOX
			
				    String mailboxID=inboxService.getMailboxID(userName);
					String message="you have created circle with the name :::  "+circleName;
		           
					mail.setMailboxID(mailboxID);
					mail.setCircleName(circleName);
					mail.setTo(userName);
					mail.setFrom(circleName);
					mail.setMessage(message);
					mail.setTimeCreated(new Date());
		            System.out.println("--------------------------------------------------");
					System.out.println("CIRCLE HAS BEEN CREATED BY USER NAME :: "+mail.getTo());
					System.out.println("CIRCLE NAME :: "+mail.getCircleName());
		            System.out.println("--------------------------------------------------");
					mail1=inboxService.updateInbox(mail);
					
					//INCREMENTING MAIL COUNT FOR THIS MAILBOX 
					inboxService.incrementMailCount(userName);
		}
		return mail1;
	}


	//---------------------------------JOIN CIRCLE------------------------------------

	@Override
	public List<Mails> joinCircle(JsonNode node) 
	{
		System.out.println("CIRCLE SERVICE ::: JOIN CIRCLE");
		Mails mailActor=new Mails();
		Mails mailTarget=new Mails();

		JsonNode actor = node.path("actor");
		String userName = actor.path("name").asText();
		JsonNode target = node.path("target");
		String targetName=target.path("name").asText();
		String circleName=target.path("circleName").asText();

		List<Mails> list=new ArrayList<Mails>();


		//to check whether user name exists or not
		String userName1=inboxService.checkUserName(userName);
		String targetName1=inboxService.checkUserName(targetName);
		
		System.out.println("USER NAME :"+userName+"  USER NAME 1 :"+userName1+"TARGET NAME : "+targetName+"TARGET NAME 1: "+targetName1+"  CIRCLE NAME :"+circleName);
			
	    if( userName1==null || targetName1==null || circleName==null || circleName=="")
		{
				System.out.println("SORRY!!! NAME FIELDS ARE EITHER EMPTY or USER NAME DOESNOT EXIST OR CIRCLE NAME IS NOT PROVIDED::: CREATE CIRCLE");
	    }
		else
		{
			//JOINING USER'S INBOX
					
			        String message= "you have joined to circle "+circleName ;
					String mailboxID =inboxService.getMailboxID(userName);
		
					mailActor.setTo(userName);
					mailActor.setFrom(circleName);
					mailActor.setMessage(message);
					mailActor.setCircleName(circleName);
					mailActor.setTimeCreated(new Date());
					mailActor.setMailboxID(mailboxID);
		
					Mails mail1=inboxService.updateInbox(mailActor);
		
					list.add(mail1);
                     
					//INCREMENTING MAIL COUNT FOR THIS MAILBOX 
					inboxService.incrementMailCount(userName);

			//NOTIFIED USER'S INBOX
					
					message= userName +"  has joined  to your circle "+circleName;
					mailboxID =inboxService.getMailboxID(targetName);
		
					mailTarget.setTo(targetName);
					mailTarget.setFrom(userName);
					mailTarget.setMessage(message);
					mailTarget.setTimeCreated(new Date());
					mailTarget.setMailboxID(mailboxID);
		
					Mails mail2=inboxService.updateInbox(mailTarget);
		
					list.add( mail2);
					
					//INCREMENTING MAIL COUNT FOR THIS MAILBOX 
					inboxService.incrementMailCount(targetName);
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

//---------------------------------END----------------------------------------------