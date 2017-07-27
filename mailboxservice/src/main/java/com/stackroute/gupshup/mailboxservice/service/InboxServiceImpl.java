package com.stackroute.gupshup.mailboxservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.mailboxservice.exception.MailboxException;
import com.stackroute.gupshup.mailboxservice.model.Mailbox;
import com.stackroute.gupshup.mailboxservice.model.Mails;
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
	public void consumeActivity(String activity) {
		System.out.println(activity);
		ObjectMapper mapper=new ObjectMapper();
		if(activity!=null && activity.length()>0) {
			JsonNode node = null;
			try {
				node = mapper.readTree(activity);

				String activityType = node.path("type").asText();
				String objectType= node.path("object").path("type").asText();
				System.out.println("activity:"+activityType);

				if(activityType.equalsIgnoreCase("Create") && objectType.equalsIgnoreCase("person")) {
					String userName=node.path("actor").path("id").asText();
					System.out.println("entering create user");	
					createMailbox(userName);
				}
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (MailboxException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Mailbox createMailbox(String userName) throws MailboxException {
		Mailbox savedMaibox = null;
		if(userName != null) {
			Mailbox mailbox = mailboxRepository.findOneByUsername(userName);
			if(mailbox != null) {
				savedMaibox = mailbox;
			}
			else {
				mailbox = new Mailbox();
				mailbox.setUsername(userName);
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
	public Mails addDeletedMail(String userName, String circleId, Mails mails) throws MailboxException {
		Mails savedMail = null ;
		if(userName != null && circleId != null && mails != null) {
			mails.setCircleId(circleId);
			mails.setUsername(userName);
			savedMail = mailsRepository.save(mails);
		}else {
			throw new MailboxException("user name cant be null");
		}
		return savedMail;
	}

	@Override
	public List<Mails> getDeletedMails(String userName, String circleID) throws MailboxException {
		List<Mails> mailslist = null;
		if(userName != null && circleID != null) {
			mailslist = mailsRepository.findAll(userName, circleID);
		}else {
			throw new MailboxException("user name cant be null");
		}
		return mailslist;
	}

	@Override
	public List<Mails> filterMails(List<Mails> mailslist, String username) throws MailboxException {
		List<Mails> filteredlist = new ArrayList<>();
		if(mailslist != null) {
			for(Mails mails : mailslist) {
				Mails mails2 = mailsRepository.findByMailIDAndUsername(mails.getMailID(), username);
				if(mails2 == null)
					filteredlist.add(mails);
			}
		}
		return filteredlist;
	}

}
