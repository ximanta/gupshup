package com.stackroute.gupshup.activityproducer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.activityproducer.domain.Add;
import com.stackroute.gupshup.activityproducer.domain.Create;
import com.stackroute.gupshup.activityproducer.domain.Dislike;
import com.stackroute.gupshup.activityproducer.domain.Follow;
import com.stackroute.gupshup.activityproducer.domain.Join;
import com.stackroute.gupshup.activityproducer.domain.Leave;
import com.stackroute.gupshup.activityproducer.domain.Like;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//-------------------------------------Activity Controller-----------------------------
@RestController
@RequestMapping("/activity/")
@Api(value="REST Controller that receives activities")
public class ActivityController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	Environment environment;
	
	//----------------------------------Join Activity-----------------------------------
	@ApiOperation(value="join activity", notes="join a circle")
	@RequestMapping(value="join", method=RequestMethod.POST)
	public ResponseEntity<Map<String,String>> join(@Valid @RequestBody Join join, BindingResult bindingResult){
		Map<String,String> message = new HashMap<String,String>();
		if(bindingResult.hasErrors()){
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error: errors){
				message.put("error-"+error.hashCode(),error.getDefaultMessage());
			}
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		try {
			kafkaTemplate.send(environment.getProperty("activityproducer.topic.circle"),new ObjectMapper().writeValueAsString(join));
		} catch (JsonProcessingException e) {
			message.put("error",e.getMessage());
			logger.error(e.toString());
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		message.put("message","Activity Published Successfully");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	//----------------------------------Leave Activity--------------------------------
	@ApiOperation(value="leave activity", notes="leave a circle")
	@RequestMapping(value="leave", method=RequestMethod.POST)
	public  ResponseEntity<Map<String,String>>  leave(@Valid @RequestBody Leave leave, BindingResult bindingResult){
		Map<String,String> message = new HashMap<String,String>();
		if(bindingResult.hasErrors()){
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error: errors){
				message.put(error.getObjectName(),error.getDefaultMessage());
			}
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		try {
			kafkaTemplate.send(environment.getProperty("activityproducer.topic.circle"),new ObjectMapper().writeValueAsString(leave));
		} catch (JsonProcessingException e) {
			message.put("error",e.getMessage());
			logger.error(e.toString());

			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		message.put("message","Activity Published Successfully");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	//----------------------------------Like Activity---------------------------------
	@ApiOperation(value="like activity", notes="like a post")
	@RequestMapping(value="like", method=RequestMethod.POST)
	public  ResponseEntity<Map<String,String>>  like(@Valid @RequestBody Like like, BindingResult bindingResult){
		Map<String,String> message = new HashMap<String,String>();
		if(bindingResult.hasErrors()){
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error: errors){
				message.put(error.getObjectName(),error.getDefaultMessage());
			}
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		try {
			kafkaTemplate.send(environment.getProperty("activityproducer.topic.circle"),new ObjectMapper().writeValueAsString(like));
		} catch (JsonProcessingException e) {
			message.put("error",e.getMessage());
			logger.error(e.toString());
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		message.put("message","Activity Published Successfully");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	//----------------------------------Dislike Activity--------------------------------
	@ApiOperation(value="dislike activity", notes="dislike a post")
	@RequestMapping(value="dislike", method=RequestMethod.POST)
	public  ResponseEntity<Map<String,String>>  dislike(@Valid @RequestBody Dislike dislike, BindingResult bindingResult){
		Map<String,String> message = new HashMap<String,String>();
		if(bindingResult.hasErrors()){
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error: errors){
				message.put(error.getObjectName(),error.getDefaultMessage());
			}
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		try {
			kafkaTemplate.send(environment.getProperty("activityproducer.topic.circle"),new ObjectMapper().writeValueAsString(dislike));
		} catch (JsonProcessingException e) {
			message.put("error",e.getMessage());
			logger.error(e.toString());
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		message.put("message","Activity Published Successfully");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	//----------------------------------Follow Activity--------------------------------
	@ApiOperation(value="follow activity", notes="follow a user")
	@RequestMapping(value="follow", method=RequestMethod.POST)
	public  ResponseEntity<Map<String,String>>  follow(@Valid @RequestBody Follow follow, BindingResult bindingResult){
		Map<String,String> message = new HashMap<String,String>();
		if(bindingResult.hasErrors()){
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error: errors){
				message.put(error.getObjectName(),error.getDefaultMessage());
			}
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		try {
			kafkaTemplate.send(environment.getProperty("activityproducer.topic.user"),new ObjectMapper().writeValueAsString(follow));
		} catch (JsonProcessingException e) {
			message.put("error",e.getMessage());
			logger.error(e.toString());
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		message.put("message","Activity Published Successfully");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	//----------------------------------Create Activity--------------------------------
	@ApiOperation(value="create activity", notes="post to a person")
	@RequestMapping(value="create", method=RequestMethod.POST)
	public  ResponseEntity<Map<String,String>>  create(@Valid @RequestBody Create create, BindingResult bindingResult){
		Map<String,String> message = new HashMap<String,String>();
		if(bindingResult.hasErrors()){
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error: errors){
				message.put(error.getObjectName(),error.getDefaultMessage());
			}
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		try {
			kafkaTemplate.send(environment.getProperty("activityproducer.topic.mailbox"),new ObjectMapper().writeValueAsString(create));
		} catch (JsonProcessingException e) {
			message.put("error",e.getMessage());
			logger.error(e.toString());
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		message.put("message","Activity Published Successfully");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	//----------------------------------Add Activity--------------------------------
	@ApiOperation(value="add activity", notes="post to a group")
	@RequestMapping(value="add", method=RequestMethod.POST)
	public  ResponseEntity<Map<String,String>>  add(@Valid @RequestBody Add add, BindingResult bindingResult){
		Map<String,String> message = new HashMap<String,String>();
		if(bindingResult.hasErrors()){
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error: errors){
				message.put(error.getObjectName(),error.getDefaultMessage());
			}
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		try {
			kafkaTemplate.send(environment.getProperty("activityproducer.topic.circle"),new ObjectMapper().writeValueAsString(add));
			
		} catch (JsonProcessingException e) {
			message.put("error",e.getMessage());
			logger.error(e.toString());
			System.out.println("here");
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
		message.put("message","Activity Published Successfully");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

}
