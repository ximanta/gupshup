package com.stackroute.gupshup.activityproducer.controller;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.stackroute.gupshup.activityproducer.producer.ActivityProducer;


@RestController
@RequestMapping("/activity/")
public class ActivityController {

	@Autowired
	ActivityProducer activityProducer;
	
	@RequestMapping(value="join", method=RequestMethod.POST)
	public String join(@RequestBody Join join){
		try {
			activityProducer.publishMessage("circle",new ObjectMapper().writeValueAsString(join));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "published";
	}
	
	@RequestMapping(value="leave", method=RequestMethod.POST)
	public String leave(@RequestBody Leave leave){
		try {
			activityProducer.publishMessage("circle",new ObjectMapper().writeValueAsString(leave));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "published";
	}
	
	@RequestMapping(value="like", method=RequestMethod.POST)
	public String like(@RequestBody Like like){
		try {
			activityProducer.publishMessage("circle",new ObjectMapper().writeValueAsString(like));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "published";
	}
	
	@RequestMapping(value="dislike", method=RequestMethod.POST)
	public String dislike(@RequestBody Dislike dislike){
		try {
			activityProducer.publishMessage("circle",new ObjectMapper().writeValueAsString(dislike));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "published";
	}
	
	@RequestMapping(value="follow", method=RequestMethod.POST)
	public String follow(@RequestBody Follow follow){
		try {
			activityProducer.publishMessage("person",new ObjectMapper().writeValueAsString(follow));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "published";
	}
	
	@RequestMapping(value="create", method=RequestMethod.POST)
	public String create(@RequestBody Create create){
		try {
			activityProducer.publishMessage("circle",new ObjectMapper().writeValueAsString(create));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "published";
	}
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	public String add(@RequestBody Add add){
		try {
			activityProducer.publishMessage("circle",new ObjectMapper().writeValueAsString(add));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "published";
	}
	
}
