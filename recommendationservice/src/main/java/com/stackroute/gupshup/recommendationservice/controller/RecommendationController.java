package com.stackroute.gupshup.recommendationservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@RequestMapping(value="/createuser", method=RequestMethod.POST)
	public ResponseEntity<Map> createUser(@RequestBody UserRecommendation userRecommendation){
		return new ResponseEntity<Map>(userRecommendationService.createUser(userRecommendation), HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/createcircle", method=RequestMethod.POST)
	public ResponseEntity<Map> createCircle(@RequestBody CircleRecommendation circleRecommendation){
		return new ResponseEntity<Map>(circleRecommendationService.createCircle(circleRecommendation), HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value="/follows/{id1}/{id2}", method=RequestMethod.GET)
	public ResponseEntity<Iterable<Map<String, Object>>> follows(@PathVariable String id1, @PathVariable String id2){
		System.out.println("controller "+id1+" "+id2);
		return new ResponseEntity<Iterable<Map<String, Object>>>(userRecommendationService.follows(id1,id2), HttpStatus.OK);
	}
	
	@RequestMapping(value="/created/{id1}/{id2}", method=RequestMethod.GET)
	public ResponseEntity<Iterable<Map<String, Object>>> created(@PathVariable String id1, @PathVariable String id2){
		System.out.println("controller "+id1+" "+id2);
		return new ResponseEntity<Iterable<Map<String, Object>>>(circleRecommendationService.created(id1,id2), HttpStatus.OK);
	}
	
	@RequestMapping(value="/subscribed/{id1}/{id2}", method=RequestMethod.GET)
	public ResponseEntity<Iterable<Map<String, Object>>> subscribed(@PathVariable String id1, @PathVariable String id2){
		
		return new ResponseEntity<Iterable<Map<String, Object>>>(circleRecommendationService.subscribed(id1,id2), HttpStatus.OK);
	}

}
