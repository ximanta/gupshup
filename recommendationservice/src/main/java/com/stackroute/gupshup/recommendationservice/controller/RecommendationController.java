package com.stackroute.gupshup.recommendationservice.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.gupshup.recommendationservice.entity.CircleRecommendation;
import com.stackroute.gupshup.recommendationservice.entity.UserRecommendation;
import com.stackroute.gupshup.recommendationservice.service.CircleRecommendationService;
import com.stackroute.gupshup.recommendationservice.service.UserRecommendationService;

@RestController
@RequestMapping("recommendation")
public class RecommendationController {
	
	@Autowired
	UserRecommendationService userRecommendationService;
	
	@Autowired
	CircleRecommendationService circleRecommendationService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Iterable<List<String>>> followFriendOfFriend(@PathVariable String id)
	{
		return new ResponseEntity<Iterable<List<String>>>(userRecommendationService.followFriendOfFriend(id), HttpStatus.FOUND );
	}
	
	@RequestMapping(value="/circle/{id}", method=RequestMethod.GET)
	public ResponseEntity<Iterable<List<String>>> subscribeRecommendation(@PathVariable String id){
		
		return new ResponseEntity<Iterable<List<String>>>(circleRecommendationService.subscribeRecommendation(id), HttpStatus.FOUND);
	}
	
	@RequestMapping(value="/createuser", method=RequestMethod.POST)
	public ResponseEntity createUser(@Valid @RequestBody UserRecommendation userRecommendation, BindingResult bindingResult){
		if(bindingResult.hasErrors())
		{
			return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(userRecommendationService.createUser(userRecommendation), HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/createcircle", method=RequestMethod.POST)
	public ResponseEntity createCircle(@Valid @RequestBody CircleRecommendation circleRecommendation, BindingResult bindingResult){
		if(bindingResult.hasErrors())
		{
			return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(circleRecommendationService.createCircle(circleRecommendation), HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value="/follows/{id1}/{id2}", method=RequestMethod.GET)
	public ResponseEntity<Iterable<Map<String, Object>>> follows(@PathVariable String id1, @PathVariable String id2){
		return new ResponseEntity<Iterable<Map<String, Object>>>(userRecommendationService.follows(id1,id2), HttpStatus.OK);
	}
	
	@RequestMapping(value="/created/{id1}/{id2}", method=RequestMethod.GET)
	public ResponseEntity<Iterable<Map<String, Object>>> created(@PathVariable String id1, @PathVariable String id2){
		return new ResponseEntity<Iterable<Map<String, Object>>>(circleRecommendationService.created(id1,id2), HttpStatus.OK);
	}
	
	@RequestMapping(value="/subscribed/{id1}/{id2}", method=RequestMethod.GET)
	public ResponseEntity<Iterable<Map<String, Object>>> subscribed(@PathVariable String id1, @PathVariable String id2){
		return new ResponseEntity<Iterable<Map<String, Object>>>(circleRecommendationService.subscribed(id1,id2), HttpStatus.OK);
	}

}
