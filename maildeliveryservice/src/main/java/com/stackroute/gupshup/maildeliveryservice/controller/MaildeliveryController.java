package com.stackroute.gupshup.maildeliveryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.maildeliveryservice.model.Circle;
import com.stackroute.gupshup.maildeliveryservice.model.Group;
import com.stackroute.gupshup.maildeliveryservice.model.Join;
import com.stackroute.gupshup.maildeliveryservice.model.Person;
import com.stackroute.gupshup.maildeliveryservice.model.User;

@RestController
public class MaildeliveryController {
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	//	
	//	@RequestMapping("/getCircles")
	//	public ResponseEntity getCircle(){
	//		RestTemplate restTemplate = new RestTemplate();
	//		ResponseEntity<Circle[]> entity = restTemplate.getForEntity("http://172.23.239.176:8080/circleservice/circle/", Circle[].class);
	//		return entity;
	//	}

	@RequestMapping("/join")
	public String getUsers(){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<User[]> userEntity = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ResponseEntity<Circle[]> circleEntity = restTemplate.getForEntity("http://172.23.238.189:8080/circleservice/circle/", Circle[].class);
			userEntity = restTemplate.getForEntity("http://172.23.238.189:8080/userservice/user", User[].class);
			User[] users = userEntity.getBody();
			Circle[] circles = circleEntity.getBody();

			for(int i=0;i<users.length;i++){
				Person person1 =new Person(null,users[i].getUserName(),"Person",users[i].getFirstName()+" "+users[i].getLastName(),null);
				for(int j=100;j<1000;j++){
					Group group =new Group(null,circles[j].getCircleId(),"Group",circles[j].getCircleName());
					Join join =new Join(null, "Join",person1.getId()+" joined "+ group.getName(), person1, group);
					kafkaTemplate.send("circle",objectMapper.writeValueAsString(join));
				}
			}
		}
		catch(Exception exception){
			System.out.println(exception);
		}
		return "done.......";
	}
	
	@RequestMapping("/publish")
	public String finalTest(){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<User[]> userEntity = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ResponseEntity<Circle[]> circleEntity = restTemplate.getForEntity("http://172.23.238.189:8080/circleservice/circle/", Circle[].class);
			userEntity = restTemplate.getForEntity("http://172.23.238.189:8080/userservice/user", User[].class);
			User[] users = userEntity.getBody();
			Circle[] circles = circleEntity.getBody();

			for(int i=0;i<users.length;i++){
				Person person1 =new Person(null,users[i].getUserName(),"Person",users[i].getFirstName()+" "+users[i].getLastName(),null);
				for(int j=100;j<1000;j++){
					Group group =new Group(null,circles[j].getCircleId(),"Group",circles[j].getCircleName());
					Join join =new Join(null, "Join",person1.getId()+" joined "+ group.getName(), person1, group);
					kafkaTemplate.send("circle",objectMapper.writeValueAsString(join));
				}
			}
		}
		catch(Exception exception){
			System.out.println(exception);
		}
		return "done.......";
	}
}
