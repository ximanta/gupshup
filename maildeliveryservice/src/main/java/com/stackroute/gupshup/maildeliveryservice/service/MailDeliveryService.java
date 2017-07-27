package com.stackroute.gupshup.maildeliveryservice.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.maildeliveryservice.model.Add;
import com.stackroute.gupshup.maildeliveryservice.model.Group;
import com.stackroute.gupshup.maildeliveryservice.model.Mail;
import com.stackroute.gupshup.maildeliveryservice.model.Note;
import com.stackroute.gupshup.maildeliveryservice.model.Person;

@Service
public class MailDeliveryService {

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	@KafkaListener(topics="circle", containerFactory = "batchFactory")
	public void getActivityType(List<String> activitylist) {
		ObjectMapper objectMapper = new ObjectMapper();

		for(String activity: activitylist ) {
			if(activity!=null && activity.length() >0) {
				JsonNode node = null;
				try {
					node = objectMapper.readTree(activity);
					String type = node.path("type").asText();
					if(type != null) {
						System.out.println("type asctivity :"+type);
						if(type.equalsIgnoreCase("add")) {
							System.out.println("in add");
							Add add = objectMapper.treeToValue(node, Add.class);
							Person person = (Person) add.getActor();
							Note note = (Note) add.getObject();
							Group group = (Group) add.getTarget();

							Mail mail = new Mail();
							mail.setCircleId(group.getId());
							mail.setFrom(person.getId());
							mail.setTo(group.getId());
							mail.setMessage(note.getContent());
							Date date = new Date();
							mail.setTimeCreated(date);
							simpMessagingTemplate.convertAndSend("/topic/message/"+ mail.getTo(), mail);
						}
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
