package com.stackroute.gupshup.circleservice.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.circleservice.exception.CircleCreationException;
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

	@Autowired
	private CircleRepository circleRepo;

	@Autowired
	private CircleServiceProducer producer;

	//-------------find all circle-----------
	@Override
	public List<Circle> findAllCircle() throws CircleCreationException {
		List<Circle> circlelist = null;
		try {
			circlelist  = circleRepo.findAll();
			if(circlelist==null) 
				throw new CircleCreationException("No circle");		
		} catch(CircleCreationException circlecreationException)	{
			circlecreationException.getMessage();
		}
		return circlelist;
	}

	//----------------reg exp for creating circle---------------
//	public static boolean testCircleName (String s){
//		Pattern pattern = Pattern.compile("\\w{3,12}");
//		Matcher matcher = pattern.matcher(s);
//		if (matcher.find()){
//			return true;
//		}
//		return false;
//	}

	//-------------------------------- create circle-----------
	public Circle createCircle(Circle circle) {
		Circle savedCircle = null;
		savedCircle=circleRepo.save(circle);
		try {
			if(circle.getCircleName()==null) {
				throw new CircleCreationException("Give Circle Name");		
			}
////			else if(testCircleName(circle.getCircleName())==false) {
////				throw new CircleCreationException("Give proper Circle Name");
////			}
//			else if(!(circle.getCircleName().length()>2 && circle.getCircleName().length() <12)) {
//				throw new CircleCreationException("Circle Name should be above 2 letters and below 12 letters");
//			}
////			else if(circle.getCircleName().substring(0,1).contains("")) {
////
////			}
			else {
				savedCircle=circleRepo.save(circle);
//				System.out.println("savedCircle :"+savedCircle);
				Person person = new Person(null,"Person",circle.getCircleCreatedBy(),circle.getCircleId());
				Group group = new Group(null,"Circle",circle.getCircleId());
				Create create = new Create("null","create",circle.getCircleCreatedBy()+" created "+circle.getCircleName(),person,group);
				producer.publishMessage("testcirclemailbox",new ObjectMapper().writeValueAsString(create));
				producer.publishMessage("testcirclerecommendation",new ObjectMapper().writeValueAsString(create));
			}
		} catch(CircleCreationException circlecreationException)	{
			circlecreationException.getMessage();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			System.out.println(e.getMessage()+"here");
		}
		return savedCircle;
	}

	//----------------find circle by id--------------------
	@Override
	public Circle findById(String id) throws CircleCreationException{
		Circle circle = null;
		try {
			if(id!=null) {
				circle = circleRepo.findOne(id);
			}
			else {
				throw new CircleCreationException("No Circle available");		
			}
		}
		catch(CircleCreationException circlecreationException)	{
			circlecreationException.getMessage();
		}
		return circle;
	}


	//------------circle is exist--------------------------
	@Override
	public boolean ifCircleExist(Circle circle) throws CircleCreationException{
		try {
			String circleid = circle.getCircleId();
			if(findById(circleid)!=null) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(CircleCreationException circlecreationException)	{
			circlecreationException.getMessage();
		}
		return true;
	}
	//-----------------update circle-------------------------
	@Override
	public void updateCircle(Circle currentCircle) {
		
		try {
			
			if(currentCircle==null)	{
				throw new CircleCreationException("Give Circle name");
			}
			else {
				
				circleRepo.save(currentCircle);
				List<User> members = getCircleMembersDetail(currentCircle.getCircleId());
				for(int i=0;i<members.size();i++){
				Person person = new Person(null,"Person",currentCircle.getCircleCreatedBy(),currentCircle.getCircleId());
				Group group = new Group(null,"Circle",currentCircle.getCircleId());
				Update update = new Update("null","update",currentCircle.getCircleCreatedBy()+" updated "+currentCircle.getCircleName(),person,group);
				producer.publishMessage("testcirclemailbox",new ObjectMapper().writeValueAsString(update));
				producer.publishMessage("testcirclerecommendation",new ObjectMapper().writeValueAsString(update));
				}
			}
		}
		catch(CircleCreationException circlecreationException)	{
			circlecreationException.getMessage();
		}
		catch(JsonProcessingException js) {
			js.getMessage();
		}

	}

	//------------delete all circles------------------------
	@Override
	public void deleteAllCircle() throws CircleCreationException {
		try {
			if(circleRepo.findAll()!=null) {
				circleRepo.deleteAll();
			}
			else {
				throw new CircleCreationException("No circle Available");
			}
		}
		catch(CircleCreationException circlecreationException) {
			circlecreationException.getMessage();
		}
	}
	
	
	//-------- list all circle member-----------
	@Override
	public List<User> getCircleMembersDetail(String circleId) throws CircleCreationException {
		List<User> members=null;
		try {
			if(circleId==null) {
				throw new CircleCreationException("No circle member Available");
			}
			else {
				Circle circle = findById(circleId);
				if(circle!=null)
				{
				members = circle.getCircleMembers();
				}
			}
		}
		catch(CircleCreationException circlecreationException) {
			circlecreationException.getMessage();
		}
		return members;
	}
	
	
	//----------delete circle-----------------------
	@Override
	public void deleteCircle(String id) throws CircleCreationException {
		try {
			if(id==null) {
				throw new CircleCreationException("Cann't delete circle");
			}
			else {
				Circle circle = new Circle();
				circle = findById(id);
				List<User> members = getCircleMembersDetail(id);
				circleRepo.delete(id);
				for(int i=0;i<members.size();i++){
				Person person = new Person(null,"Person",members.get(i).getUserId(),circle.getCircleId());
				Group group = new Group(null,"Circle",circle.getCircleId());
				Delete delete = new Delete("null","delete",circle.getCircleCreatedBy()+" deleted the "+circle.getCircleName(),person,group);
				producer.publishMessage("testcirclemailbox",new ObjectMapper().writeValueAsString(delete));
				producer.publishMessage("testcirclerecommendation",new ObjectMapper().writeValueAsString(delete));
				}
				
			}
		}
		catch(CircleCreationException circlecreationException)	{
			circlecreationException.getMessage();
		}
		catch(JsonProcessingException js) {
			js.getMessage();
		}
	}
	
	//-------------------------add mail to the mailbox-----------
    @Override
    public void addMailtoMailbox(String circleId,Mail mail) throws CircleCreationException {
        try {
            if(circleId!=null) {
                Circle circle = findById(circleId);
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
            else {
                throw new CircleCreationException("Cann't delete circle");
            }
        }
        catch(CircleCreationException circlecreationException)    {
            circlecreationException.getMessage();
        }
    }
	
	
	//--------------add circle members----------------------------
	@Override
	public Circle addCircleMember(String circleId, User user) throws CircleCreationException {
		List<User> members=null;
		Circle circle = null;
		try {
			if(circleId==null) {
				throw new CircleCreationException("No circle member added");
			}
			else {
				circle = circleRepo.findOne(circleId);
				System.out.println(circle);
				members=circle.getCircleMembers();
				members.add(user);
				circle.setCircleMembers(members);
				circleRepo.save(circle);
			}
		}
		catch(CircleCreationException circlecreationException) {
			circlecreationException.getMessage();
		}
		return circle;
	}
	
	@Override
	public Circle deleteCircleMember(String circleId, String userId) throws CircleCreationException {
		List<User> members=null;
		Circle circle = null;
		int index = -1;
		try {
			if(circleId==null || userId==null) {
				throw new CircleCreationException("circle member can't be deleted");
			}
			else {
				circle = circleRepo.findOne(circleId);
				members=circle.getCircleMembers();

				for(int i=0;i< members.size();i++)
				{
					if(members.get(i).getUserId() == userId)
					{
						index = i;break;
					}
				}
				if(index>=0)
					members.remove(index);
				circle.setCircleMembers(members);
				circleRepo.save(circle);

			}
		}	
		catch(CircleCreationException circlecreationException) {
			circlecreationException.getMessage();
		}
		return circle;
	}

	public void getActivityType(JsonNode node) {
		String type = node.path("type").asText();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			
			if(type!=null) {
				
				//-------------join--------------------------------
				   if(type.equalsIgnoreCase("join")){
	                    Join join = objectMapper.treeToValue(node, Join.class);
	                    Group group = (Group) join.getObject();
	                    String circleId = group.getName();
	                    Person person = (Person) join.getActor();
	                    User user = new User();
	                    user.setUserId(person.getName());

	                    addCircleMember(circleId, user);
	                    Mail mail=new Mail();
	                    mail.setTo(circleId);
	                    mail.setFrom(user.getUserId());
	                    mail.setMailID(new Date().toString());
	                    mail.setTimeCreated(new Date().toString());
	                    mail.setMessage(person.getName()+"has joined"+group.getContext());
	                    addMailtoMailbox(circleId, mail);
	                    List<User> members = getCircleMembersDetail(circleId);

	                    if(members!=null)
	                    {
	                        for(int i=0;i<members.size();i++){
	                            System.out.println(members.get(i).getUserId());
	                            Group person1 = new Group(circleId,"Group",members.get(i).getUserId());
	                            System.out.println(person1.getName());
	                            Join addActivity = new Join(join.getContext(),join.getType(),join.getSummary(),join.getActor(),person1);
	                            producer.publishMessage("person",objectMapper.writeValueAsString(addActivity));
	                            producer.publishMessage("testcirclerecommendation",objectMapper.writeValueAsString(addActivity));
	                            
	                        }
	                    }

	                }
				   
//	           	//--------------------create------------------------
//				if(type.equalsIgnoreCase("create")){
//					Create create = objectMapper.treeToValue(node, Create.class);
//					Group group = (Group) create.getObject();
//					String circleId = group.getName();
//					Person person = (Person) create.getActor(); 
//					User user = new User();
//					user.setUserId(person.getName());
//					addCircleMember(circleId, user);
//					List<User> members = getCircleMembersDetail(circleId);
//					//System.out.println(members.size());
//					//System.out.println(members);
//					for(int i=0;i<members.size();i++){
//						System.out.println(members.get(i).getUserName());
//						Person person1 = new Person(null,"Person",members.get(i).getUserId(),circleId);
//						Add addActivity = new Add(create.getContext(),create.getType(),create.getSummary(),create.getActor(),create.getObject(),person1);
//						producer.publishMessage("person",objectMapper.writeValueAsString(addActivity));
//					}				
//					
//				}

				//--------------leave-------------------------------------
				if(type.equalsIgnoreCase("leave")){

                    Leave leave = objectMapper.treeToValue(node, Leave.class);
                    Group group = (Group) leave.getObject();
                    String circleId = group.getName();
                    Person person = (Person) leave.getActor();
                    
                    List<User> members = getCircleMembersDetail(circleId);
                    deleteCircleMember(circleId, person.getName());

                    for(int i=0;i<members.size();i++){
                        System.out.println(members.get(i).getUserId());
                        Person person1 = new Person(null,"Person",members.get(i).getUserId(),circleId);
                        Leave leaveActivity = new Leave(leave.getContext(),leave.getType(),leave.getSummary(),leave.getActor(),person1);
                        producer.publishMessage("person",objectMapper.writeValueAsString(leaveActivity));
                    }
                }
				
				//-----To add and message in circle --------------------------------------------
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

	                List<User> members = getCircleMembersDetail(circleId);

	                if(members!=null)
	                {
	                    for(int i=0;i<members.size();i++){
	                        Person person2 = new Person(null,"Person",members.get(i).getUserId(),circleId);
	                        Add addActivity = new Add(add.getContext(),add.getType(),add.getSummary(),add.getActor(),add.getObject(),person2);
	                        producer.publishMessage("person",objectMapper.writeValueAsString(addActivity));
	                    }
	                }
	            }
	        }
		}
	        catch (IOException e) {
	            e.getMessage();
	        } catch (CircleCreationException e) {
	            e.getMessage();
	        }
	
	
	}
}
