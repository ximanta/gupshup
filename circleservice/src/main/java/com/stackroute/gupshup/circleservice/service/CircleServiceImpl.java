package com.stackroute.gupshup.circleservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.circleservice.exception.CircleException;
import com.stackroute.gupshup.circleservice.model.Add;
import com.stackroute.gupshup.circleservice.model.Circle;
import com.stackroute.gupshup.circleservice.model.Delete;
import com.stackroute.gupshup.circleservice.model.Group;
import com.stackroute.gupshup.circleservice.model.Join;
import com.stackroute.gupshup.circleservice.model.Leave;
import com.stackroute.gupshup.circleservice.model.Mailbox;
import com.stackroute.gupshup.circleservice.model.Member;
import com.stackroute.gupshup.circleservice.model.Note;
import com.stackroute.gupshup.circleservice.model.Person;
import com.stackroute.gupshup.circleservice.repository.CircleRepository;
import com.stackroute.gupshup.circleservice.repository.MailboxRepository;
import com.stackroute.gupshup.circleservice.repository.MemberRepository;


@Service
public class CircleServiceImpl implements CircleService{

	@Autowired
	CircleRepository circleRepo;

	@Autowired
	MemberRepository memberRepo;

	@Autowired
	MailboxRepository mailboxRepo;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	Environment environment;

	//-------------------List all circles---------------------------------------
	@Override
	public List<Circle> listAllCircles() throws CircleException{
		List<Circle> circleList = circleRepo.findAll();
		if(circleList == null) {
			throw new CircleException("No Circle");
		}
		return circleList;
	}

	//-----------------------Create a circle-------------------------------------
	@Override
	public Circle createCircle(Circle circle) throws CircleException,JsonProcessingException{
		Circle savedCircle = null;
		Circle circle1 = circleRepo.findByCircleName(circle.getCircleName());
		//System.out.println("circle name:"+circle1.getCircleName());
		if(circle1 == null) 
		{
			//System.out.println("here"+circle1.getCircleName()+"circlename");
			if(circle.getCircleName() == null) {
				throw new CircleException("Give circle name");
			}
			else {
				savedCircle = circleRepo.save(circle);
				Date date = new Date();
				circle.setCircleCreatedDate(date.toString());
				circleRepo.save(savedCircle);
	
				Member member = new Member();
				member.setUsername(circle.getCircleCreatedBy());
				member.setCircleId(circle.getCircleId());
				member.setAccessTime(date);
				member.setCircleName(circle.getCircleName());
				addCircleMember(member);
	
				Mailbox mail = new Mailbox();
				mail.setTo(circle.getCircleId());
				mail.setCircleId(circle.getCircleId());
				mail.setFrom(circle.getCircleCreatedBy());
				mail.setMessage(circle.getCircleCreatedBy()+" created a circle "+circle.getCircleName());
				mail.setTimeCreated(date);
				addMails(mail);
	
	
				//			Person person = new Person(null, circle.getCircleCreatedBy(), "Person", null, null);
				//			Group group = new Group(null, circle.getCircleId(), "Group", circle.getCircleName());
				//			Create createActivity = new Create(null, "create", null, person, group);
				//			ObjectMapper objectMapper = new ObjectMapper();
				//			kafkaTemplate.send(environment.getProperty("circleservice.topic.mailbox"), objectMapper.writeValueAsString(createActivity));
				
				Person person = new Person(null, circle.getCircleCreatedBy(), "person", null, null);
				Map<String, Object> recommendActivity = new HashMap<>();
				recommendActivity.put("type", "create");
				recommendActivity.put("actor", person);
				Map<String, Object> group = new HashMap<>();
				group.put("type", "group");
				group.put("id", circle.getCircleId());
				group.put("name", circle.getCircleName());
				group.put("keywords", circle.getKeywords());
				recommendActivity.put("object", group);
				ObjectMapper objectMapper = new ObjectMapper();
				kafkaTemplate.send(environment.getProperty("circleservice.topic.recommendation"), objectMapper.writeValueAsString(recommendActivity));
				
			}
		}
		else {
			throw new CircleException("circle already existed");
		}
		return savedCircle;
	}

	//-------------------------Update circle-----------------------------------------
	@Override
	public Circle updateCircle(Circle circle) throws CircleException, JsonProcessingException {
		Circle updatedCircle = new Circle();
		if(circle == null || circle.getCircleId() == null) {
			throw new CircleException("Give circle name");
		}
		else {
			updatedCircle = circleRepo.save(circle);

			Date date = new Date();
			Mailbox mail = new Mailbox();
			mail.setCircleId(circle.getCircleId());
			mail.setFrom(updatedCircle.getCircleCreatedBy());
			mail.setTo(updatedCircle.getCircleId());
			mail.setMessage(updatedCircle.getCircleCreatedBy()+" updated circle "+updatedCircle.getCircleId());
			mail.setTimeCreated(date);
			addMails(mail);

			Person person = new Person(null, circle.getCircleCreatedBy(), "person", null, null);
			Map<String, Object> recommendActivity = new HashMap<>();
			recommendActivity.put("type", "update");
			recommendActivity.put("actor", person);
			Map<String, Object> group = new HashMap<>();
			group.put("type", "group");
			group.put("id", circle.getCircleId());
			group.put("name", circle.getCircleName());
			group.put("description", circle.getCircleDescription());
			group.put("keywords", circle.getKeywords());
			recommendActivity.put("object", group);
			ObjectMapper objectMapper = new ObjectMapper();
			kafkaTemplate.send(environment.getProperty("circleservice.topic.recommendation"), objectMapper.writeValueAsString(recommendActivity));
			
		}
		return updatedCircle;
	}

	//--------------------Add members to a circle-------------------------------------
	@Override
	public void addCircleMember(Member member) throws CircleException {
		if(member == null) {
			throw new CircleException("member not found");
		}
		else {
			Circle circle = circleRepo.findOne(member.getCircleId());
			circle.setTotalUsers(circle.getTotalUsers()+1);
			circleRepo.save(circle);
			memberRepo.save(member);
		}
	}

	//------------------------Add mails to mailbox-------------------------------------
	@Override
	public void addMails( Mailbox mailbox ) {
		messagingTemplate.convertAndSend("/topic/message/"+mailbox.getTo(), mailbox);
		Circle circle = circleRepo.findOne(mailbox.getCircleId());
		circle.setTotalmails(circle.getTotalmails()+1);
		circleRepo.save(circle);
		mailboxRepo.save(mailbox);
	}

	//-----------------------------getActivity------------------------------------------
	@Override
	@KafkaListener(topics="circle")
	public void getActivityType(String activity) throws CircleException {
		ObjectMapper objectMapper = new ObjectMapper();

		if(activity!=null && activity.length() >0) {
			JsonNode node = null;
			try {
				node = objectMapper.readTree(activity);
				String type = node.path("type").asText();
				if(type != null) {
					System.out.println("type asctivity :"+type);
					//----------------------Join Activity--------------------------
					if(type.equalsIgnoreCase("join")) {
						Join join = objectMapper.treeToValue(node, Join.class);
						Group group = (Group) join.getObject();
						Person person = (Person) join.getActor();
						
						Member member = memberRepo.findOneByCircleIdAndUsername(group.getId(), person.getId());
						System.out.println("here afetr checking memnber");
						if(member == null) {
							System.out.println("**********************************");
						String profilePicture = person.getImage();
						String circleId = group.getId();
						Member member1 = new Member();
						member1.setCircleId(circleId);
						member1.setProfilePicture(profilePicture);
						member1.setUsername(person.getId());
						Circle circle = findByID(circleId);
						member1.setCircleName(circle.getCircleName());
						addCircleMember(member1);

						Mailbox mail = new Mailbox();
						mail.setCircleId(circleId);
						mail.setFrom(person.getId());
						mail.setTo(circleId);
						mail.setMessage(person.getName()+" joined circle ");
						Date date = new Date();
						mail.setTimeCreated(date);
						addMails(mail);
						
						Person person1 = new Person(null, person.getId(), "person", null, null);
						Group group1 = new Group(null, group.getId(), "group", group.getName());
						Join join1 = new Join(null, "join", null, person1, group1);
						ObjectMapper objectMapper1 = new ObjectMapper();
						kafkaTemplate.send(environment.getProperty("circleservice.topic.recommendation"), objectMapper1.writeValueAsString(join1));

						}
						else
						{
							System.out.println("member already joined");
						}

						//						Person person1 = new Person(null, circle.getCircleCreatedBy(), "Person", null, null);
						//						Group group1 = new Group(null, circle.getCircleId(), "Group", circle.getCircleName());
						//						Create createActivity = new Create(null, "create", null, person1, group1);
						//						ObjectMapper objectMapper1 = new ObjectMapper();
						//						kafkaTemplate.send(environment.getProperty("circleservice.topic.mailbox"), objectMapper1.writeValueAsString(createActivity));
						
					}
						
						

					//-----------------------Leave Activity-----------------------------------
					if(type.equalsIgnoreCase("leave")) {
						Leave leave = objectMapper.treeToValue(node, Leave.class);
						Group group = (Group) leave.getObject();
						Person person = (Person) leave.getActor();

						//memberRepo.delete(person.getId());
						Member member = memberRepo.findOneByCircleIdAndUsername(group.getId(), person.getId());
						memberRepo.delete(member);

						Circle circle = circleRepo.findOne(group.getId());
						circle.setTotalUsers(circle.getTotalUsers()-1);
						circleRepo.save(circle);
						

						Mailbox mail = new Mailbox();
						mail.setCircleId(group.getId());
						mail.setFrom(person.getId());
						mail.setTo(group.getId());
						mail.setMessage(person.getId()+" left the circle "+circle.getCircleName());
						Date date = new Date();
						mail.setTimeCreated(date);
						addMails(mail);
						
						Person person2 = new Person(null, person.getId(), "person", null, null);
						Group group2 = new Group(null, group.getId(), "group", group.getName());
						Leave leave2 = new Leave(null, "leave", null, person2, group2);
						ObjectMapper objectMapper2 = new ObjectMapper();
						kafkaTemplate.send(environment.getProperty("circleservice.topic.recommendation"), objectMapper2.writeValueAsString(leave2));
					}

					//---------------------------Add Activity---------------------------------
					if(type.equalsIgnoreCase("add")) {
						System.out.println("in add");
						Add add = objectMapper.treeToValue(node, Add.class);
						Person person = (Person) add.getActor();
						Note note = (Note) add.getObject();
						Group group = (Group) add.getTarget();

						Mailbox mail = new Mailbox();
						mail.setCircleId(group.getId());
						mail.setFrom(person.getId());
						mail.setTo(group.getId());
						mail.setMessage(note.getContent());
						Date date = new Date();
						mail.setTimeCreated(date);
						addMails(mail);
					}
				}

				}
		 catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//-------------------------get mailbox by circlerId--------------------------------
	@Override
	public List<Mailbox> getMails(String circleId, String username, int page) throws CircleException {

		Member member = memberRepo.findOneByCircleIdAndUsername(circleId, username);

		if(member == null) {
			throw new CircleException("no mailbox existed");
		}
		else {
			
			Circle circle = findByID(circleId);
			Date date = new Date();
			member.setAccessTime(date);
			member.setReadMails(circle.getTotalmails());
			memberRepo.save(member);
			
			Pageable pageable = new PageRequest(page,10,Sort.Direction.DESC,"timeCreated");
			List<Mailbox> mails = mailboxRepo.findAllMails(circleId, pageable);
			return mails;
		}
	}

	//--------------------------get members by circleId---------------------------------------
	@Override
	public List<Member> getMembers(String circleId) throws CircleException {
		List<Member> members = null;
		if(circleId != null) {
			members = memberRepo.findAllMemeber(circleId);
			if(members == null) {
				throw new CircleException("give circle not existed");
			}
		}
		return members;
	}

	//-----------------------------get circles by userid-----------------------------------------
	@Override
	public List<Member> getCircles(String username) throws CircleException {
		List<Member> members = null;
		List<Member> members1 = new ArrayList<>();
		if(username != null)
			members = memberRepo.findAllCircle(username);
		for(Member member:members) {
			Circle circle = findByID(member.getCircleId());
			member.setUnreadMails(circle.getTotalmails() - member.getReadMails());
			member.setStatus(1);
			memberRepo.save(member);
			Member member1 = new Member(circle.getCircleId(), circle.getCircleName(), member.getUnreadMails());
			members1.add(member1);
		}
		return members1;
	}

	//------------------------------get circle by circleId---------------------------------------
	@Override
	public Circle findByID(String circleId) {
		Circle circle = null;
		if(circleId != null)
			circle = circleRepo.findOne(circleId);
		return circle;
	}

	//------------------------------delete circle--------------------------------------------------
	@Override
	public void deleteCircle(String circleId) {
		Circle deleteCircle  = null;
		if(circleId != null) {
			deleteCircle = circleRepo.findOne(circleId);
			circleRepo.delete(circleId);
			
			Person person = new Person(null, deleteCircle.getCircleCreatedBy(), "person", null, null);
			Group group = new Group(null, deleteCircle.getCircleId(), "group", deleteCircle.getCircleName());
			Delete deleteActivity = new Delete(null, "delete", null, person, group);
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				System.out.println("here in delete circle method");
				kafkaTemplate.send(environment.getProperty("circleservice.topic.recommendation"), objectMapper.writeValueAsString(deleteActivity));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	//-----------------------------changeStatus----------------------------------------------------
	@Override
	@MessageMapping("/logout/{userName}")
	public void changeStatus(@DestinationVariable String userName) {
		if(userName != null) {
			List<Member> members = memberRepo.findAllCircle(userName);
			for(Member member : members) {
				member.setStatus(0);
				memberRepo.save(member);
			}
		}
	}
	

}

