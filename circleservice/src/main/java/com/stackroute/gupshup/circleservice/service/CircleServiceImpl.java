package com.stackroute.gupshup.circleservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.circleservice.exception.CircleException;
import com.stackroute.gupshup.circleservice.model.Add;
import com.stackroute.gupshup.circleservice.model.Circle;
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
		Circle savedCircle = circleRepo.save(circle);
		if(circle.getCircleName() == null) {
			throw new CircleException("Give circle name");
		}
		else {
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

		}
		return updatedCircle;
	}

	//--------------------Add members to a circle-------------------------------------
	@Override
	public void addCircleMember(Member member) {
		Circle circle = circleRepo.findOne(member.getCircleId());
		circle.setTotalUsers(circle.getTotalUsers()+1);
		circleRepo.save(circle);
		memberRepo.save(member);
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
					//----------------------Join Activity--------------------------
					if(type.equalsIgnoreCase("join")) {
						Join join = objectMapper.treeToValue(node, Join.class);
						Group group = (Group) join.getObject();
						Person person = (Person) join.getActor();

						String profilePicture = person.getImage();
						String circleId = group.getId();
						Member member = new Member();
						member.setCircleId(circleId);
						member.setProfilePicture(profilePicture);
						member.setUsername(person.getId());
						Circle circle = findByID(circleId);
						member.setCircleName(circle.getCircleName());
						addCircleMember(member);

						Mailbox mail = new Mailbox();
						mail.setCircleId(circleId);
						mail.setFrom(person.getId());
						mail.setTo(circleId);
						mail.setMessage(person.getName()+" joined circle ");
						Date date = new Date();
						mail.setTimeCreated(date);
						addMails(mail);

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

						memberRepo.delete(person.getId());
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
					}

					//---------------------------Add Activity---------------------------------
					if(type.equalsIgnoreCase("add")) {
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//-------------------------get mailbox by circlerId--------------------------------
	@Override
	public List<Mailbox> getMails(String circleId, String username, int page) throws CircleException {

		Member member = memberRepo.findOneByCircleIdAndUsername(circleId, username);

		Circle circle = findByID(circleId);
		Date date = new Date();
		member.setAccessTime(date);
		member.setReadMails(circle.getTotalmails());
		memberRepo.save(member);

		Pageable pageable = new PageRequest(page,10,Sort.Direction.DESC,"timeCreated");
		List<Mailbox> mails = mailboxRepo.findAllMails(circleId, pageable);
		return mails;
	}

	//--------------------------get members by circleId---------------------------------------
	@Override
	public List<Member> getMembers(String circleId) throws CircleException {
		List<Member> members = null;
		if(circleId != null)
			members = memberRepo.findAllMemeber(circleId);
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
		if(circleId != null)
			circleRepo.delete(circleId);
	}

}

