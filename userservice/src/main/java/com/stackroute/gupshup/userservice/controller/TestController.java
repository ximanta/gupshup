package com.stackroute.gupshup.userservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping(path="/hello",method=RequestMethod.GET)
	public String hello(){
		return "hello";
	}
	
	@RequestMapping(path="/welcome",method=RequestMethod.GET)
	public String welcome(){
		return "welcome";
	}
	
}
