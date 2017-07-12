package com.stackroute.gupshup.mailboxservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.mailboxservice.exception.MailboxException;
import com.stackroute.gupshup.mailboxservice.model.Mailbox;
import com.stackroute.gupshup.mailboxservice.model.Mails;
import com.stackroute.gupshup.mailboxservice.model.Message;
import com.stackroute.gupshup.mailboxservice.repository.MailboxRepository;
import com.stackroute.gupshup.mailboxservice.repository.MailsRepository;

@Service
public class InboxServiceImpl implements InboxService {

	@Autowired
	MailboxRepository mailboxRepository;

	@Autowired
	MailsRepository mailsRepository;
	
	@Autowired
	Environment environment;
	
	@Override
	@KafkaListener(topics="mailbox")
	public void consumeActivity(String activity) 
	{
		ObjectMapper mapper=new ObjectMapper();
		if(activity!=null && activity.length()>0)
		{
			JsonNode node = null;
			try {
				node = mapper.readTree(activity);

				System.out.println("entering activity");

				String activityType = node.path("type").asText();
				String objectType= node.path("object").path("type").asText();
				System.out.println("activity:"+activityType);

				if(activityType.equalsIgnoreCase("Create") && objectType.equalsIgnoreCase("person"))
				{
					String userName=node.path("actor").path("id").asText();
					System.out.println("entering create circle");	
					createMailbox(userName);
				}

				else
				{
					String userName=node.path("actor").path("id").asText();
					String circleID=node.path("object").path("id").asText();
					System.out.println("entering join circle");
					addCircle(userName, circleID);
				}
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MailboxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public Mailbox createMailbox(String userName) throws MailboxException {
		Mailbox savedMaibox = null;
		if(userName!=null) {
			Mailbox mailbox = mailboxRepository.findOneByUsername(userName);
			if(mailbox!=null) {
				savedMaibox=mailbox;
			}
			else {
				mailbox=new Mailbox();
				mailbox.setUsername(userName);
				mailbox.setCirclelist(new ArrayList<>());
				mailbox.setMailboxCreatedDate(new Date());
				mailbox.setMailCount(0);
				savedMaibox = mailboxRepository.save(mailbox);
			}
		}
		else {
			throw new MailboxException("user name cant be null");
		}
		return savedMaibox;
	}

	@Override
	public Mailbox addCircle(String userName, String circleID) throws MailboxException {
		Mailbox save=null;
		if(userName!=null && circleID!=null) {
			save=mailboxRepository.findOneByUsername(userName);
			if(save!=null) {
				List<String> circlelist = save.getCirclelist();
				if(circlelist!=null) {
					int index = circlelist.indexOf(circleID);
					if(index==-1) {
						circlelist.add(circleID);
						save.setCirclelist(circlelist);
						mailboxRepository.save(save);
					}
					else {
						throw new MailboxException("circle already exists");
					}
				}
			}
		}
		return save;
	}


	@Override
	public Mails addDeletedMessage(String userName, String circleID, String mailID) throws MailboxException 
	{
		Mails mails=null;
		if(userName==null || circleID==null || mailID ==null)
		{
			throw new MailboxException("fields are null");
		}
		else 
		{
			Mailbox mailbox=mailboxRepository.findOneByUsername(userName);
			if(mailbox!=null)
			{
				mails=new Mails();
				mails.setCircleID(circleID);
				mails.setDeletedDate(new Date());
				mails.setUserName(userName);
				mails.setMailID(mailID);
				mails=mailsRepository.save(mails);
			}

		}
		return mails;
	}

	@Override
	public List<Mails> getDeletedMails(String userName,String circleID) 
	{

		return mailsRepository.findAll(userName, circleID);

	}

	@Override
	public List<Message> getMessages(String userName, String circleID, int page) 
	{
		RestTemplate restTemplate=new RestTemplate();	
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		params.put("circleID", circleID);
		params.put("page", page);
		
		ResponseEntity<Message[]> entity = restTemplate.getForEntity(environment.getProperty("circleservice-address")+"/circle/{circleID}/mailbox?userName={userName}&page={page}" ,Message[].class,params);
		List<Message> messages = Arrays.asList(entity.getBody());
		List<Message> newMessages=new ArrayList<Message>();

		List<Mails> mailboxMails = mailsRepository.findAll(userName, circleID);

		for(Mails mails : mailboxMails)
		{
			String mailID=mails.getMailID();
			for(Message msg:messages)
			{
				if(!msg.getMailID().equalsIgnoreCase(mailID))
				{
					newMessages.add(msg);
				}
			}
		}

		return newMessages;
	}


}
