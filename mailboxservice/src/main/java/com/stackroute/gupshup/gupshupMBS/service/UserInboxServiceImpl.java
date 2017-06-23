
package com.stackroute.gupshup.gupshupMBS.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.gupshupMBS.model.Mailbox;
import com.stackroute.gupshup.gupshupMBS.model.Mails;

@Service
public class UserInboxServiceImpl implements UserInboxService 
{
	@Autowired
	InboxService inboxService;



	//----------------------TO CREATE A USER MAILBOX WITH DEFAULT MESSAGE-------------
	@Override
	public Mailbox createUser(JsonNode node) 
	{
		Mails mail=new Mails();
		Mailbox mailbox = null;
		JsonNode actor = node.path("actor");
		String userName = actor.path("name").asText();


		if(userName=="")
		{
			System.out.println("Name Field is empty ");
		}
		else
		{
			//creating a empty mailbox for a registered user
			inboxService.createMailBoxService(userName);


			//update newly created mailbox with default message
			mail.setTo(userName);
			String message="Mailbox has been successfully created for"+userName;
			mail.setMessage(message);
			System.out.println("=================Inside create user method============");
			System.out.println("=================createUser::userName"+mail.getTo());
			mailbox=inboxService.updateInbox(mail);
		}
		return mailbox;
	}


	//----------------------TO DELETE A USER MAILBOX-----------------------------------
	@Override
	public void deleteUser(JsonNode node) 
	{

		JsonNode actor = node.path("actor");
		String userName = actor.path("name").asText();
		if(userName=="")
		{
			System.out.println("Name field is empty");
		}
		else
		{
			//Delete the  mailbox for the particular user
			inboxService.deleteMailBoxService(userName);
		}
	}


	//---------------------TO NOTIFY THE USER ABOUT UPDATION IN USER PROFILE-----------
	@Override
	public Mailbox updateUser(JsonNode node) 
	{
		Mails mailActor=new Mails();

		JsonNode actor = node.path("actor");
		String updatedUserName = actor.path("name").asText();
		JsonNode object = node.path("object");
		String updatedType=object.path("type").asText();
		Mailbox m1=null;
		if(updatedUserName==null && updatedType==null)
		{
			System.out.println("name fields are either null or not specified correctly : UPDATE USER");
		} 
		else
		{
			//---- notifying user who has updated -----------
			mailActor.setTo(updatedUserName);
			String message= "you have updated your profile"+updatedType;
			mailActor.setMessage(message);
			m1=inboxService.updateInbox(mailActor);
		}
		return m1;

	}


	//--------------------TO NOTIFY BOTH WHO FOLLOWS AND WHO IS BEING FOLLOWED-----------
	@Override
	public List<Mailbox> followUser(JsonNode node)
	{
		Mails mailActor=new Mails();
		Mails mailTarget=new Mails();
		System.out.println("entering follow user");
		JsonNode actor = node.path("actor");
		String followerUserName = actor.path("name").asText();
		JsonNode target = node.path("target");
		String targetUserName = target.path("targetName").asText();
		List<Mailbox> list=new ArrayList<Mailbox>();

		if( followerUserName==null && targetUserName==null )
		{
			System.out.println("name fields are either null or not specified correctly: FOLLOW USER METHOD");
		}
		else
		{
			//---------followed user-------
			mailActor.setTo(targetUserName);
			mailActor.setFrom(followerUserName);
			String message= followerUserName+" is following you";
			mailActor.setMessage(message);
			Mailbox m1=inboxService.updateInbox(mailActor);
			list.add(m1);

			//----------following user----
			mailTarget.setTo(followerUserName);
			mailTarget.setFrom(targetUserName);
			String messageTarget= "you are following "+targetUserName;
			mailTarget.setMessage(messageTarget);
			Mailbox m2=inboxService.updateInbox(mailTarget);
			list.add(m2);
		}
		return list;
	}



	//-----------------------DIRECT MESSAGING---------------------------------------- 
	@Override
	public Mailbox directMessage(JsonNode node) 
	{
		Mails targetMail=new Mails();
		JsonNode actor = node.path("actor");
		String fromUserName = actor.path("name").asText();
		String message=actor.path("content").asText();
		JsonNode target = node.path("target");
		String toUserName=target.path("name").asText();
		Mailbox mailBox=null;
		
		if( fromUserName==null && toUserName==null && message==null )
		{
			System.out.println("name fields are either null or not specified correctly : DIRECT MESSAGES");
		}
		else
		{
		//setting inbox mail to the recipient
		targetMail.setTo(toUserName);
		targetMail.setFrom(fromUserName);
		targetMail.setMessage(message);
		mailBox=inboxService.updateInbox(targetMail);
		}
		return mailBox;
	}

}

//-----------------------------------END----------------------------------------------