package com.stackroute.gupshup.gupshupMBS.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.stackroute.gupshup.gupshupMBS.model.Mailbox;
import com.stackroute.gupshup.gupshupMBS.model.Mails;
import com.stackroute.gupshup.gupshupMBS.repository.MailBoxRepository;

//import scala.annotation.meta.setter;

@Service
public class InboxServiceImpl implements InboxService
{

	@Autowired
	MailBoxRepository mailBoxRepository;

	@Autowired
	UserInboxService userInboxService;

	@Autowired
	CircleInboxService circleInboxService;


	//--------------------------------TO VIEW USER MAILBOX---------------------------
	
	@Override
	public Mailbox viewMailBoxService(String name) 
	{
		Mailbox mail=new Mailbox();
		System.out.println("INSIDE VIEW MAILBOX METHOD");

		for(Mailbox mailbox:mailBoxRepository.findAll())
		{
			String userName=mailbox.getUserName();
			System.out.println("USER NAME : "+userName);
			if(userName.equals(name))
			{
				mail=mailbox;
			}

		}
		return mail;
     }

	//--------------------------------TO CREATE USER MAILBOX---------------------------

	@Override
	public Mailbox createMailBoxService(String userName)
	{

		Mailbox mailBox = new Mailbox();
		System.out.println("============createMailBox::userName"+userName);
		Boolean test=true;
		for(Mailbox mailbox:mailBoxRepository.findAll())
		{
			String curName=mailbox.getUserName();
			if(userName.equals(curName))
			{
				test=false;
			}
		}

		if(test==true)
		{
			mailBox.setUserName(userName);
			mailBox.setInbox(new ArrayList<>());
			mailBox.setOutbox(new ArrayList<>());
			return mailBoxRepository.save(mailBox);
		}
		else
			return null;
	}

	//--------------------------------TO DELETE USER MAILBOX---------------------------

	@Override
	public void deleteMailBoxService(String userName) 
	{
		for(Mailbox mailbox:mailBoxRepository.findAll())
		{
			String curName=mailbox.getUserName();
			if(userName.equals(curName))
			{
				mailBoxRepository.delete(mailbox.getMailboxID());	
			}
		}

	}


	//--------------------------------TO DELETE ALL MAILBOXES---------------------------
	@Override
	public void flushInboxDb() {
		// TODO Auto-generated method stub
		mailBoxRepository.deleteAll();
	}

	//---------------------------UPDATING USER INBOX-----------------------------------


	//---------------------------ADDING/UPDATING INBOX MAILS---------------------------------
	@Override
	public Mailbox updateInbox(Mails mail) 
	{

		System.out.println("inside updateInbox:: user name:  "+mail.getTo());
		String userName=mail.getTo();
		Mailbox mailBox=null;
		for(Mailbox innerMailBox:mailBoxRepository.findAll())
		{
			String curUserName=innerMailBox.getUserName();
			if(userName.equals(curUserName))
			{

				mailBox=innerMailBox;
			}
		}

		Mails currentMail=new Mails();
		//	currentMail.setMailID(x);
		//x++;
		currentMail.setTo(mail.getTo());
		currentMail.setFrom(mail.getFrom());
		currentMail.setMessage(mail.getMessage());
		List<Mails> list = new ArrayList<Mails>();
		list=mailBox.getInbox();
		System.out.println("list details"+list);
		list.add(currentMail);
		mailBox.setInbox(list);
		return mailBoxRepository.save(mailBox);
	}

	//--------------------------------DELETING INBOX MAILS---------------------------

	@Override
	public Mailbox deleteInbox(Mails mail) 
	{
		String userName=mail.getTo();
		Mailbox mailBox=null;
		for(Mailbox innerMailBox:mailBoxRepository.findAll())
		{
			String curUserName=innerMailBox.getUserName();
			if(userName==curUserName)
			{
				mailBox=innerMailBox;
			}
		}

		List<Mails> currentMailList=mailBox.getInbox();
		List<Mails> updatedList=new ArrayList<Mails>();

		for(Mails mails1:currentMailList)
		{
			if(mails1.getFrom()!=mail.getFrom() && mails1.getMessage()!=mail.getMessage())
			{
				updatedList.add(mails1);
			}
		}
		mailBox.setInbox(null);
		mailBox.setInbox(updatedList);
		return mailBoxRepository.save(mailBox);

	}
	//--------------------------------TO CHECK TYPE OF ACTIVITY---------------------------	
    @Override
	
    public void checkActivityType(JsonNode node) 
	{

		System.out.println("entering activity");
		String activityType = node.path("type").asText();
		System.out.println("activity:"+activityType);

		if(activityType.equalsIgnoreCase("CreateCircle"))
		{
			System.out.println("entering create circle");	
			circleInboxService.createCircle(node);
		}

		else if(activityType.equals("JoinCircle")) {
			System.out.println("entering join circle");
			circleInboxService.joinCircle(node);
		}

		else if(activityType.equals("LeaveCircle"))
		{
			System.out.println("entering leave circle");
			circleInboxService.leaveCircle(node);	
		}

		else if(activityType.equals("DeleteCircle"))
		{
			System.out.println("entering delete circle");
			circleInboxService.deleteCircle(node);
		}

		else if(activityType.equals("PostCircle"))
		{
			System.out.println("entering post circle");
			circleInboxService.messageCircle(node);
		}

		else if(activityType.equals("UpdateCircle"))
		{
			System.out.println("entering update circle");
			circleInboxService.messageCircle(node);
		}

		else if(activityType.equals("CreateUser"))
		{
			System.out.println("entering creating user");
			userInboxService.createUser(node);
		}


		else if(activityType.equals("DeleteUser"))
		{
			System.out.println("entering deleting user");
			userInboxService.deleteUser(node);
		}

		else if(activityType.equals("FollowUser"))
		{
			System.out.println("entering follow user");
			userInboxService.followUser(node);
		}

		else if(activityType.equals("UpdateUser"))
		{
			System.out.println("entering update user");
			userInboxService.updateUser(node);
		}

		else if(activityType.equals("DirectMessage"))
		{
			System.out.println("entering direct message :user");
			userInboxService.directMessage(node);
		}

		else
		{
			System.out.println("----------------No Activity as such-----------------------------------------------");
		}

	}

}


//----------------------------------------END------------------------------------------
