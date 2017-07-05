package com.stackroute.gupshup.circleservice.service;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.circleservice.exception.CircleException;
import com.stackroute.gupshup.circleservice.model.Add;
import com.stackroute.gupshup.circleservice.model.Circle;
import com.stackroute.gupshup.circleservice.model.Create;
import com.stackroute.gupshup.circleservice.model.Delete;
import com.stackroute.gupshup.circleservice.model.Group;
import com.stackroute.gupshup.circleservice.model.Join;
import com.stackroute.gupshup.circleservice.model.Leave;
import com.stackroute.gupshup.circleservice.model.Mail;
import com.stackroute.gupshup.circleservice.model.Note;
import com.stackroute.gupshup.circleservice.model.Person;
import com.stackroute.gupshup.circleservice.model.Update;
import com.stackroute.gupshup.circleservice.model.User;
import com.stackroute.gupshup.circleservice.producer.CircleServiceProducer;
import com.stackroute.gupshup.circleservice.repository.CircleRepository;


@Service
public class CircleServiceImpl implements CircleService {


	//----------------------------Autowired circle repository-------------------------
	@Autowired
	private CircleRepository circleRepo;

	//----------------------------Autowired circle service producer---------------------------------
	@Autowired
	private CircleServiceProducer producer;

	//----------------------------Autowired Environment-----------------------------------
	@Autowired
	private Environment environment;

	//----------------------------Find all circle-----------
	@Override
	public List<Circle> findAllCircles() throws CircleException {
		List<Circle> circlelist = circleRepo.findAll();
		if(circlelist==null) 
			throw new CircleException("No circle");		
		return circlelist;
	}

	//----------------------------Create circle----------------------------------
	public Circle createCircle(Circle circle) throws CircleException, JsonProcessingException{
		Circle savedCircle = circleRepo.save(circle);

		if(circle.getCircleName()==null) 
			throw new CircleException("Give Circle Name");		
		else {
			Date date = new Date();
			circle.setCircleCreatedDate(date.toString());
			savedCircle=circleRepo.save(circle);

			// TODO : get user details from user service
			User user = new  User();
			user.setUsername(savedCircle.getCircleCreatedBy());
			addCircleMember(savedCircle.getCircleId(),user);

			Person person = new Person(null,savedCircle.getCircleCreatedBy(),"Person",null, null);
			Group group = new Group(null,savedCircle.getCircleId(),"Circle",savedCircle.getCircleName());
			String message = savedCircle.getCircleCreatedBy()+" created "+savedCircle.getCircleName();

			Create create = new Create(null,"Create", message, person, group);
			producer.publishMessage(environment.getProperty("circleservice.topic.mailbox"),new ObjectMapper().writeValueAsString(create));
			producer.publishMessage(environment.getProperty("circleservice.topic.recommendation"),new ObjectMapper().writeValueAsString(create));
		}
		return savedCircle;
	}

	//----------------------------Find circle by id--------------------
	@Override
	public Circle findById(String id) throws CircleException {
		Circle circle = null;
		if(id!=null) 
			circle = circleRepo.findOne(id);
		else {
			throw new CircleException("No Circle available");		
		}
		return circle;
	}


	//-----------------------------Update circle-------------------------
	@Override
	public void updateCircle(Circle circle) throws CircleException ,JsonProcessingException{
		if(circle == null || circle.getCircleId() == null)	{
			throw new CircleException("Give Circle name");
		}
		else {
			Circle updatedCircle = circleRepo.save(circle);
			List<User> members = getCircleMembers(updatedCircle.getCircleId());
			for(int i=0;i<members.size();i++){
				User user = new  User();
				user.setUsername(updatedCircle.getCircleCreatedBy());

				Person person = new Person(null,updatedCircle.getCircleCreatedBy(),"Person",null, null);
				Group group = new Group(null,updatedCircle.getCircleId(),"Circle",updatedCircle.getCircleName());
				String message = updatedCircle.getCircleCreatedBy()+" updated "+updatedCircle.getCircleName();

				Update update = new Update("null","update",message,person,group);
				producer.publishMessage(environment.getProperty("circleservice.topic.mailbox"),new ObjectMapper().writeValueAsString(update));
				producer.publishMessage(environment.getProperty("circleservice.topic.recommendation"),new ObjectMapper().writeValueAsString(update));
			}
		}
	}

	//------------------------------Delete all circles------------------------
	@Override
	public void deleteAllCircle() throws CircleException {
		if(circleRepo.findAll()!=null) {
			circleRepo.deleteAll();
		}
		else {
			throw new CircleException("No circle Available");
		}
	}

	//-------------------------------List all circle member-----------
	@Override
	public List<User> getCircleMembers(String circleId) throws CircleException {
		List<User> members=null;
		Circle circle = findById(circleId);

		if(circle == null) {
			throw new CircleException("No circle Available");
		}
		else {
			members = circle.getCircleMembers();
			if(members == null || members.size() == 0)
			{
				throw new CircleException(" no member found");
			}
		}
		return members;
	}

	//--------------------delete circle-----------------------
	@Override
	public void deleteCircle(String id) throws CircleException, JsonProcessingException {
		if(id == null) {
			throw new CircleException("Cann't delete circle");
		}
		else {
			Circle circle = findById(id);
			List<User> members = getCircleMembers(id);
			circleRepo.delete(id);

			for(int i=0;i<members.size();i++){
				Person person = new Person(null,circle.getCircleCreatedBy(),"Person",null, null);
				Group group = new Group(null,circle.getCircleId(),"Circle",circle.getCircleName());
				String message = circle.getCircleCreatedBy()+" deleted "+circle.getCircleName();

				Delete delete = new Delete("null","Delete", message, person, group);
				producer.publishMessage(environment.getProperty("circleservice.topic.mailbox"),new ObjectMapper().writeValueAsString(delete));
				producer.publishMessage(environment.getProperty("circleservice.topic.recommendation"),new ObjectMapper().writeValueAsString(delete));
			}

		}
	}

	//----------------------add mail to the mailbox-----------
	@Override
	public void addMailtoMailbox(String circleId,Mail mail) throws CircleException {
		System.out.println(circleId + "circleId");
		if(circleId != null) {
			Circle circle = findById(circleId);
			if(circle != null){
				List<Mail> mailbox = circle.getMailbox();
				if(mailbox != null)
					mailbox.add(mail);
				else{
					mailbox = new ArrayList<>();
					mailbox.add(mail);
				}
				circle.setMailbox(mailbox);
				circleRepo.save(circle);
			}
		}
		else {
			throw new CircleException("Circle not found");
		}
	}


	//------------------------add circle members----------------------------
	@Override
	public Circle addCircleMember(String circleId, User user) throws CircleException {
		List<User> members=null;
		Circle circle = null;

		if(circleId==null) {
			throw new CircleException("Circle not found");
		}
		else {
			circle = circleRepo.findOne(circleId);
			if(circle != null){
				members=circle.getCircleMembers();
				boolean flag = false;
				for(User u:members){
					if(u.getUsername().equals(user.getUsername())){
						flag  = true;
						break;
					}
				}
				if(!flag)
					members.add(user);
				circle.setCircleMembers(members);
				circleRepo.save(circle);
			}
		}
		return circle;
	}


	//-----------------------delete circle members------------------------------------
	@Override
	public Circle deleteCircleMember(String circleId, String userName) throws CircleException {
		List<User> members=null;
		Circle circle = null;
		int index = -1;

		if(circleId==null || userName==null) {
			throw new CircleException("circle member can't be deleted");
		}
		else {
			circle = circleRepo.findOne(circleId);
			members=circle.getCircleMembers();
			for(int i=0;i< members.size();i++) {
				if(members.get(i).getUsername() == userName) {
					index = i; break;
				}
			}
			if(index >= 0)
				members.remove(index);
			circle.setCircleMembers(members);
			circleRepo.save(circle);
		}
		return circle;
	}

	//-----------------------------get Activity-----------------------------------------
	public void getActivityType(JsonNode node) throws CircleException,JsonProcessingException{
		String type = node.path("type").asText();
		ObjectMapper objectMapper = new ObjectMapper();

		System.out.println(type);
		if(type!=null) {
			//---------------------Join--------------------------------
			if(type.equalsIgnoreCase("join")){
				System.out.println("here");
				Join join = objectMapper.treeToValue(node, Join.class);
				Group group = (Group) join.getObject();
				Person person = (Person) join.getActor();
				String profilePicture = person.getImage();

				String circleId = group.getId();
				User user = new User();
				user.setUsername(person.getId());
				user.setFullname(person.getName());
				user.setProfilePicture(profilePicture);

				Circle circle = null;
				circle = addCircleMember(circleId, user);
				System.out.println(circleId + " "+ user.getUsername());

				if(circle != null){
					String message = user.getUsername()+" has joined "+circle.getCircleName();
					System.out.println(message);
					Mail mail=new Mail();
					mail.setTo(circleId);
					mail.setFrom(user.getUsername());
					mail.setMailID(circleId+new Date().toString());
					mail.setTimeCreated(new Date());
					mail.setMessage(message);

					addMailtoMailbox(circleId, mail);
					List<User> members=null;
					members = getCircleMembers(circleId);

//					if(members!=null) {
//						for(int i=0;i<members.size();i++) {
//							User user2 = members.get(i);
//							Person person2 = new Person(null,user2.getUsername(),"Person",user2.getFullname(),null);
//							Join join2 = new Join(join.getContext(),join.getType(),message,person2,group);
//							producer.publishMessage(environment.getProperty("circleservice.topic.mailbox"),objectMapper.writeValueAsString(join2));
//							producer.publishMessage(environment.getProperty("circleservice.topic.recommendation"),objectMapper.writeValueAsString(join2));
//						}
//					}
				}
			}

			//--------------------------Leave-------------------------------------
			if(type.equalsIgnoreCase("leave")){

				Leave leave = objectMapper.treeToValue(node, Leave.class);
				Group group = (Group) leave.getObject();
				Person person = (Person) leave.getActor();
				String circleId = group.getName();
				Circle circle = findById(circleId);
				if(circle != null){
					String message = person.getName()+" had left "+circle.getCircleName();
					List<User> members = getCircleMembers(circleId);
					if(members!=null && members.size()>0){
						deleteCircleMember(circleId, person.getName());
						for(int i=0;i<members.size();i++){
							User user2 = members.get(i);
							Person person2 = new Person(null,user2.getUsername(),"Person",user2.getFullname(),null);
							Leave leave2 = new Leave(leave.getContext(),leave.getType(),message,person2,group);
							producer.publishMessage(environment.getProperty("circleservice.topic.mailbox"),objectMapper.writeValueAsString(leave2));
							producer.publishMessage(environment.getProperty("circleservice.topic.recommendation"),objectMapper.writeValueAsString(leave2));
						}
					}
				}
			}

			//---------------------------To add a message in circle --------------------------------------------
			if(type.equalsIgnoreCase("add")) {
				Add add = objectMapper.treeToValue(node,Add.class);
				Group group = (Group) add.getTarget();
				String circleId = group.getId();
				Note note = (Note) add.getObject();
				Person person = (Person) add.getActor();

				Mail mail=new Mail();
				mail.setTo(circleId);
				mail.setFrom(person.getId());
				mail.setMailID(circleId+new Date().toString());
				mail.setTimeCreated(new Date());
				mail.setMessage(note.getContent());

				addMailtoMailbox(circleId, mail);

				List<User> members = getCircleMembers(circleId);

				if(members!=null)
				{
					for(int i=0;i<members.size();i++){
					User user2 = members.get(i);
					Person person2 = new Person(null,user2.getUsername(),"Person",user2.getFullname(),null);
					Add addActivity = new Add(add.getContext(),add.getType(),add.getSummary(),add.getActor(),add.getObject(),person2);
					producer.publishMessage(environment.getProperty("circleservice.topic.mailbox"),objectMapper.writeValueAsString(addActivity));
					}
				}
			}
		}
	}
}

