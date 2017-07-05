package com.stackroute.gupshup.mailboxservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.mailboxservice.exception.ActivityException;
import com.stackroute.gupshup.mailboxservice.model.Mailbox;
import com.stackroute.gupshup.mailboxservice.model.Mails;
import com.stackroute.gupshup.mailboxservice.repository.*;

@Service
public class InboxServiceImpl implements InboxService
{

	@Autowired
	MailboxRepository mailBoxRepository;

	@Autowired
	MailsRepository mailsRepository;

	@Autowired
	UserInboxService userInboxService;

	@Autowired
	CircleInboxService circleInboxService;

	//--------------------------------TO VIEW USER MAILBOX (REST)-------------------------
	@Override
	public List<Mails> viewMailBox(String name) 
	{
		List<Mails> list = new ArrayList<Mails>();

		for(Mails mail:mailsRepository.findAll()) {
			String userName=mail.getTo();
			if(userName.equals(name)) {
				list.add(mail);
			}

		}
		return list;
	}

	//-----------------------------TO VIEW USER INBOX MAILS (REST)---------------------------
	public List<Mails> viewInboxMails(String userName) 
	{
		System.out.println("INSIDE VIEW USER INBOX MAILS METHOD ::: USER NAME: "+userName);
		List<Mails> mails=new ArrayList<Mails>();

		for(Mails mail:mailsRepository.findAll()) {
			String curName=mail.getTo();
			//System.out.println("USER NAME : "+Name);
			if(userName.equals(curName))
			{
				mails.add(mail);
			}

		}
		return mails;
	}

	//--------------------------------TO CREATE USER MAILBOX---------------------------
	@Override
	public Mailbox createMailBox(String userName)
	{

		Mailbox mailBox = new Mailbox();
		System.out.println("INSIDE CREATE MAILBOX ::: userName: "+userName);
		Boolean test=true;

		for(Mailbox mailbox:mailBoxRepository.findAll())
		{
			String curName=mailbox.getUsername();
			if(userName.equals(curName))
			{
				test=false;
			}
		}

		if(test==true)
		{

			mailBox.setUsername(userName);
			mailBox.setMailboxCreatedDate(new Date());
			mailBox.setMailCount(1);

			return mailBoxRepository.save(mailBox);
		}
		else
			return null;
	}

	//--------------------------------UPDATE MAIL COUNTS FOR A MAILBOX-----------------

	@Override
	public void incrementMailCount(String userName)
	{
		System.out.println("THE MAIL COUNT FOR USER  ::::"+userName);
		//INCREMENTING MAIL COUNT FOR THIS MAILBOX 
		Mailbox mailBox= getMailbox(userName);
		long count=mailBox.getMailCount()+1;
		System.out.println("COUNT ::::"+count);
		mailBox.setMailCount(count);
		mailBoxRepository.save(mailBox);
	}

	@Override
	public void decrementMailCount(String userName)
	{
		System.out.println("THE MAIL COUNT FOR USER  ::::"+userName);
		//DECREMENTING MAIL COUNT FOR THIS MAILBOX 
		Mailbox mailBox= getMailbox(userName);
		long count=mailBox.getMailCount()-1;
		System.out.println("COUNT ::::"+count);
		mailBox.setMailCount(count);
		mailBoxRepository.save(mailBox);
	}


	//--------------------------------TO DELETE USER MAILBOX---------------------------

	@Override

	public void deleteMailBox(String userName) 
	{
		System.out.println("INSIDE DELETE MAILBOX METHOD ::: USER NAME : "+userName);

		String mailboxID = getMailboxID(userName);
		System.out.println(" MAILS OF MAILBOX ID : "+ mailboxID);
		System.out.println("--------------------------------");

		//DELETING ALL THE MAILS FOR THE CURRENT USER MAILBOX 
		for(Mails mail:mailsRepository.findAll())
		{
			String curMailboxID = mail.getMailboxID();
			if(mailboxID.equalsIgnoreCase(curMailboxID))
			{
				mailsRepository.delete(mail.getMailID());
				System.out.println(" DELETETED MAIL ID : "+mail.getMailID());
			}
		}

		//DELETING THE MAILBOX 
		for(Mailbox mailbox:mailBoxRepository.findAll())
		{
			String curName=mailbox.getUsername();
			if(userName.equals(curName))
			{
				mailBoxRepository.delete(mailbox.getMailboxID());	
			}
		}



	}

	//--------------------TO CHECK WHETHER MAILBOX FOR A USER EXIST OR NOT--------------
	@Override
	public String checkUserName(String userName)
	{
		if(userName.equalsIgnoreCase(null) || userName=="")
		{
			return null;
		}
		else
		{
			boolean status=false;
			for(Mailbox mailBox:mailBoxRepository.findAll())
			{
				String currentUserName=mailBox.getUsername();
				if(currentUserName.equalsIgnoreCase(userName))
				{
					status=true;
				}
			}
			if(status)
				return userName;
			else
				return null;
		}
	}

	//--------------------------TO GET MAILBOXID FROM USERNAME-------------------------------
	@Override
	public String getMailboxID(String userName)
	{
		String mailboxID="";
		for(Mailbox mailBox:mailBoxRepository.findAll())
		{
			String currentUserName = mailBox.getUsername();
			if(currentUserName.equalsIgnoreCase(userName))
				mailboxID=mailBox.getMailboxID();
		}
		return mailboxID;
	}

	//------------------------TO GET MAILBOX FOR PARTICULAR USER-----------------------

	@Override
	public Mailbox getMailbox(String userName)
	{
		Mailbox mailBox1=null;
		for(Mailbox mailBox:mailBoxRepository.findAll())
		{
			String currentUserName = mailBox.getUsername();
			if(currentUserName.equalsIgnoreCase(userName))
				mailBox1=mailBox;
		}
		return mailBox1;
	}


	//---------------------------UPDATING USER INBOX-----------------------------------------


	//---------------------------ADDING/UPDATING INBOX MAILS---------------------------------

	@Override
	public Mails updateInbox(Mails mail) 
	{

		System.out.println("INSIDE UPDATE INBOX MAIL ::: USER NAME : "+mail.getTo());
		return mailsRepository.save(mail);
	}

	//--------------------------------DELETING INBOX MAILS (REST)---------------------------

	@Override
	public String deleteInbox(Mails mail) 
	{
		boolean status=false;
		for(Mails mails:mailsRepository.findAll())
		{
			String mailId=mails.getMailID();
			if(mailId.equalsIgnoreCase(mail.getMailID())) {
				status=true;
			}
		}
		if(status)
		{
			mailsRepository.delete(mail.getMailID());
			System.out.println("MAIL DELETED FOR MAIL ID ::: "+mail.getMailID());
			decrementMailCount(mail.getTo());
			return "successfully deleted";
		}
		else
			return "mailId doesnot exist";
	}

	//--------------------------------TO CHECK TYPE OF ACTIVITY---------------------------	
	@Override
	@KafkaListener(topics = "mailbox")
	public void checkActivityType(@Payload String activity, @Header(name=KafkaHeaders.RECEIVED_PARTITION_ID) int partitionID ) {
		System.out.println(activity +" from partition "+partitionID);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = null;
		
		try {
			node = mapper.readTree(activity);
			String activityType = node.path("type").asText();

			System.out.println(activityType);
			
			if(activityType.equalsIgnoreCase("Create")) {
				String target = node.path("object").path("name").asText();
				if(target.equalsIgnoreCase("Group"))
					circleInboxService.createCircle(node);
				if(target.equalsIgnoreCase("Person"))
					userInboxService.createUser(node);
			}
			else if(activityType.equals("Delete")) {
				String target = node.path("object").path("name").asText();
				if(target.equalsIgnoreCase("Group"))
					circleInboxService.deleteCircle(node);
				if(target.equalsIgnoreCase("Person"))
					userInboxService.deleteUser(node);
			}
			//		else if(activityType.equals("Update")) {
			//			String target = node.path("object").path("name").asText();
			//			if(target.equalsIgnoreCase("Group"))
			//				circleInboxService.updateCircle(node);
			//			if(target.equalsIgnoreCase("Person"))
			//				userInboxService.updateUser(node);
			//		}
			else if(activityType.equals("Join")) {
				circleInboxService.joinCircle(node);
			}
			else if(activityType.equals("Leave")) {
				circleInboxService.leaveCircle(node);	
			}
			else if(activityType.equals("Follow")) {
				userInboxService.followUser(node);
			}
			else if(activityType.equals("Add")) {
				circleInboxService.messageCircle(node);
			}
			else
				throw new ActivityException(activityType+": "+"not found");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ActivityException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
