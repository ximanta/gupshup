package com.stackroute.gupshup.circleservice.service;
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


	//--------------------auto wired circle repository-------------------------
	@Autowired
	private CircleRepository circleRepo;

	//--------------------auto wired circle service producer---------------------------------
	@Autowired
	private CircleServiceProducer producer;

	//----------------------------------Environment auto wired-----------------------------------
	@Autowired
	private Environment environment;

	//--------------------find all circle-----------
	@Override
	public List<Circle> findAllCircles() throws CircleException {
		List<Circle> circlelist = circleRepo.findAll();
		if(circlelist==null) 
			throw new CircleException("No circle");		
		return circlelist;
	}

	//---------------------create circle----------------------------------
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
			user.setUserName(savedCircle.getCircleCreatedBy());

			addCircleMember(savedCircle.getCircleId(),user);
			Person person = new Person(null,"Person",circle.getCircleCreatedBy(),circle.getCircleId());
			Group group = new Group(null,"Circle",circle.getCircleId());
			String message = circle.getCircleCreatedBy()+" created "+circle.getCircleName();
			Note note = new Note(null,"Note",message,message);
			Add add = new Add(null, "Add", message, person, note, person);
			producer.publishMessage(environment.getProperty("circleservice.mailbox-topic"),new ObjectMapper().writeValueAsString(add));
			Create create = new Create(null,"Create", message, person, group);
			producer.publishMessage(environment.getProperty("circleservice.recommendation-topic"),new ObjectMapper().writeValueAsString(create));
		}
		return savedCircle;
	}

	//----------------find circle by id--------------------
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


	//-----------------update circle-------------------------
	@Override
	public void updateCircle(Circle currentCircle) throws CircleException ,JsonProcessingException{
		if(currentCircle==null)	{
			throw new CircleException("Give Circle name");
		}
		else {
			circleRepo.save(currentCircle);
			List<User> members = getCircleMembers(currentCircle.getCircleId());
			for(int i=0;i<members.size();i++){
				Person person = new Person(null,environment.getProperty("circleservice.c-person"),currentCircle.getCircleCreatedBy(),currentCircle.getCircleId());
				Group group = new Group(null,"Circle",currentCircle.getCircleId());
				Update update = new Update("null","update",currentCircle.getCircleCreatedBy()+" updated "+currentCircle.getCircleName(),person,group);
				producer.publishMessage(environment.getProperty("circleservice.mailbox-topic"),new ObjectMapper().writeValueAsString(update));
				producer.publishMessage(environment.getProperty("circleservice.recommendation-topic"),new ObjectMapper().writeValueAsString(update));
			}
		}
	}

	//------------------delete all circles------------------------
	@Override
	public void deleteAllCircle() throws CircleException {
		if(circleRepo.findAll()!=null) {
			circleRepo.deleteAll();
		}
		else {
			throw new CircleException("No circle Available");
		}
	}

	//-------------------list all circle member-----------
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
				Person person = new Person(null,"Person",members.get(i).getUserName(),circle.getCircleId());
				Group group = new Group(null,"Circle",circle.getCircleId());

				String message = circle.getCircleCreatedBy()+" deleted "+circle.getCircleName();
				Note note = new Note(null,"Note",message,message);
				Add add = new Add(null, "Add", message, person, note, person);
				producer.publishMessage(environment.getProperty("circleservice.mailbox-topic"),new ObjectMapper().writeValueAsString(add));

				Delete delete = new Delete("null","Delete", message, person, group);
				producer.publishMessage(environment.getProperty("circleservice.recommendation-topic"),new ObjectMapper().writeValueAsString(delete));
			}

		}
	}

	//----------------------add mail to the mailbox-----------
	@Override
	public void addMailtoMailbox(String circleId,Mail mail) throws CircleException {

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
					if(u.getUserName().equals(user.getUserName())){
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
				if(members.get(i).getUserName() == userName) {
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
		if(type!=null) {
			//---------------------join--------------------------------
			if(type.equalsIgnoreCase("join")){
				Join join = objectMapper.treeToValue(node, Join.class);
				Group group = (Group) join.getObject();
				String circleId = group.getName();
				Person person = (Person) join.getActor();

				// TODO : add user details from  user service
				User user = new User();
				user.setUserName(person.getName());

				Circle circle = addCircleMember(circleId, user);

				if(circle != null){
					Mail mail=new Mail();
					mail.setTo(circleId);
					mail.setFrom(user.getUserName());
					mail.setMailID(new Date().toString());
					mail.setTimeCreated(new Date().toString());
					mail.setMessage(person.getName()+" has joined "+circle.getCircleName());
					addMailtoMailbox(circleId, mail);

					List<User> members = getCircleMembers(circleId);

					if(members!=null) {
						for(int i=0;i<members.size();i++) {
							System.out.println(members.get(i).getUserName());
							Group person1 = new Group(circleId,"Group",members.get(i).getUserName());
							System.out.println(person1.getName());
							Note note = new Note(join.getContext(),"Note",null,person.getName()+"has joined"+circle.getCircleName());
							Add addActivity = new Add(join.getContext(),"Add",join.getSummary(),join.getActor(),note,person1);
							producer.publishMessage(environment.getProperty("circleservice.mailbox-topic"),objectMapper.writeValueAsString(addActivity));
							producer.publishMessage(environment.getProperty("circleservice.recommendation-topic"),objectMapper.writeValueAsString(addActivity));
						}
					}
				}
			}

			//--------------------------leave-------------------------------------
			if(type.equalsIgnoreCase("leave")){

				Leave leave = objectMapper.treeToValue(node, Leave.class);
				Group group = (Group) leave.getObject();
				String circleId = group.getName();
				Person person = (Person) leave.getActor();

				List<User> members = getCircleMembers(circleId);
				deleteCircleMember(circleId, person.getName());

				for(int i=0;i<members.size();i++){
					System.out.println(members.get(i).getUserName());
					Person person1 = new Person(null,"Person",members.get(i).getUserName(),circleId);
					Note note = new Note(leave.getContext(),"Note",null,person1.getName()+"has left"+group.getContext());
					Add leaveActivity = new Add(leave.getContext(),"Add",leave.getSummary(),leave.getActor(),note,person1);
					producer.publishMessage(environment.getProperty("circleservice.mailbox-topic"),objectMapper.writeValueAsString(leaveActivity));
					producer.publishMessage(environment.getProperty("circleservice.recommendation-topic"),objectMapper.writeValueAsString(leaveActivity));
				}
			}

			//---------------------------To add a message in circle --------------------------------------------
			if(type.equalsIgnoreCase("add")) {
				Add add = objectMapper.treeToValue(node,Add.class);
				Group group = (Group) add.getTarget();
				String circleId = group.getName();
				Note note = (Note) add.getObject();
				Person person = (Person) add.getActor();

				Mail mail=new Mail();
				mail.setTo(circleId);
				mail.setFrom(person.getName());
				mail.setMailID(new Date().toString());
				mail.setTimeCreated(new Date().toString());
				mail.setMessage(note.getContent());
				System.out.println(note.getContent());

				addMailtoMailbox(circleId, mail);

				List<User> members = getCircleMembers(circleId);

				if(members!=null)
				{
					for(int i=0;i<members.size();i++){
						Person person2 = new Person(null,"Person",members.get(i).getUserName(),circleId);
						Add addActivity = new Add(add.getContext(),add.getType(),add.getSummary(),add.getActor(),add.getObject(),person2);
						producer.publishMessage(environment.getProperty("circleservice.mailbox-topic"),objectMapper.writeValueAsString(addActivity));
					}
				}
			}
		}
	}
}

