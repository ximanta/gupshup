package com.stackroute.gupshup.circleservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stackroute.gupshup.circleservice.controller.hateoas.LinkAssembler;
import com.stackroute.gupshup.circleservice.exception.CircleException;
import com.stackroute.gupshup.circleservice.model.Circle;
import com.stackroute.gupshup.circleservice.model.Mailbox;
import com.stackroute.gupshup.circleservice.model.Member;
import com.stackroute.gupshup.circleservice.service.CircleService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping(value = "circle")
public class CircleController {

	@Autowired
	private CircleService circleService;

	@Autowired
	private LinkAssembler linkAssembler;
	
	

	public void setLinkAssembler(LinkAssembler linkAssembler) {
		this.linkAssembler = linkAssembler;
	}

	//-----------------------Get all circle---------------------------------------
	@ApiOperation(value = "List All Circles", notes = "To get all circles")
	@RequestMapping(value = "", method=RequestMethod.GET)
	public ResponseEntity<List<Circle>> listAllCircles() {
		List<Circle> circleList = null;
		Circle circleLinks = null;
		try {
			circleList = circleService.listAllCircles();
			linkAssembler.assembleLinksForCircleList(circleList);
			if(circleList == null) {
				return new ResponseEntity<List<Circle>>(circleList,HttpStatus.NO_CONTENT);
			}
		} catch (CircleException e) {
			return new ResponseEntity<List<Circle>>(circleList,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Circle>>(circleList,HttpStatus.OK);
	}

	//-------------------------Create a circle-----------------------------------
	@ApiOperation(value = "Create a circle", notes = "To create a circle")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Circle> createCircle(@Valid @RequestBody Circle circle, BindingResult result) {
		Circle circlesave = null;
		Circle circleLinks = null;
		if(result.hasErrors()){
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
		try {
			if(circle.getCircleName()==null) {
				return new ResponseEntity<Circle>(circlesave,HttpStatus.BAD_REQUEST);
			}
			else {
				circlesave=circleService.createCircle(circle);
				
				if(circlesave.getCircleName() == null) {
					throw new CircleException("circle already existed");
				}
			}
		} catch(CircleException circleCreationException){
			return new ResponseEntity<Circle>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new ResponseEntity<Circle>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Circle>(circleLinks, HttpStatus.CREATED);
	}

	//---------------------------Update Circle-------------------------------------
	@ApiOperation(value = "update circle", notes = "to update the circle details")
	@RequestMapping(value = "/{circleId}", method = RequestMethod.PUT)
	public ResponseEntity<Circle> updateCircle(@PathVariable String circleId, @RequestBody Circle circle) {
		try {
			if(circle.getCircleName() == null) {
				return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
			}
			else {
				circleService.updateCircle(circle);
			}
		} catch (CircleException e) {
			return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
		} catch(JsonProcessingException e) {
			e.printStackTrace();
			return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Circle>(circle,HttpStatus.OK);
	}

	//--------------------------get circle by circleId----------------------------------------
	@ApiOperation(value = "get circle by circleId", notes = "to get circle by circleId")
	@RequestMapping(value = "/{circleId}", method = RequestMethod.GET)
	public ResponseEntity<Circle> findCirclebyId(@PathVariable String circleId) {
		Circle circle = null;
		try {
			if(circleId != null) {
				circle = circleService.findByID(circleId);
			}
			else {
				return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Circle>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Circle>(circle, HttpStatus.OK);
	}

	//--------------------------delete circle by circleId---------------------------------------
	@ApiOperation(value = "delete circle", notes = "delete circle by circleId")
	@RequestMapping(value = "/{circleId}", method = RequestMethod.DELETE)
	public ResponseEntity<Circle> deleteCircle(@PathVariable String circleId) {
		try {
			if(circleId != null) {
				circleService.deleteCircle(circleId);
			}
			else {
				return new ResponseEntity<Circle>(HttpStatus.NO_CONTENT);
			}
		} catch (CircleException e) {
			return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Circle>(HttpStatus.OK);
	}

	//----------------------------Get mailbox by CircleId------------------------------
	@ApiOperation(value = "get mailbox", notes = "get mailbox by circleid")
	@RequestMapping(value ="/{circleId}/mailbox", params = {"userName", "page"}, method = RequestMethod.GET)
	public ResponseEntity<List<Mailbox>> getMails(@PathVariable String circleId, @RequestParam String userName, @RequestParam int page) {
		List<Mailbox> mails = null;
		try {
			if (circleId != null) {
				mails = circleService.getMails(circleId , userName, page);
			}
			else {
				return new ResponseEntity<List<Mailbox>>(HttpStatus.NO_CONTENT);
			}
		} catch (CircleException e) {
			return new ResponseEntity<List<Mailbox>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Mailbox>>(mails,HttpStatus.OK);
	}

	//--------------------------get members by circleId-------------------------------
	@ApiOperation(value = "get members by circleid", notes = "to get members by circleId")
	@RequestMapping(value = "/{circleId}/members", method = RequestMethod.GET)
	public ResponseEntity<List<Member>> getMembers(@PathVariable String circleId) {
		List<Member> member = null;
		try {
			if(circleId != null) {
				member = circleService.getMembers(circleId);
			} 
			else {
				return new ResponseEntity<List<Member>>(HttpStatus.NO_CONTENT);
			}
		} catch (CircleException e) {
			return new ResponseEntity<List<Member>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Member>>(member,HttpStatus.OK);
	}

	//--------------------------get circles by userId------------------------------------
	@ApiOperation(value = "get circles by userId", notes = "to get circles by userId")
	@RequestMapping(value = "/{userName}/circles", method = RequestMethod.GET)
	public ResponseEntity<List<Member>> getCircles(@PathVariable String userName) {
		List<Member> circle = null;
		try {
			if(userName != null) {
				circle = circleService.getCircles(userName);
			}
			else {
				return new ResponseEntity<List<Member>>(HttpStatus.NO_CONTENT);
			}
		} catch (CircleException e) {
			return new ResponseEntity<List<Member>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Member>>(circle,HttpStatus.OK);
	}


}
