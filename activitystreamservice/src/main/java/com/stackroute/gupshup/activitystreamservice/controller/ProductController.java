package com.stackroute.gupshup.activitystreamservice.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.gupshup.activitystreamservice.producer.ActivityProducer;



@RestController
public class ProductController {

	@Autowired
	ActivityProducer activityProducer;
	
	@RequestMapping("/activity/{message}")
	public String publishMessage(@PathVariable String message){
		activityProducer.publishMessage("notopic",message);
		return "published";
	}
}
