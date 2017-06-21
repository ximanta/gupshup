package com.stackroute.gupshup.circleservice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.gupshup.circleservice.controller.hateoas.LinkAssembler;
import com.stackroute.gupshup.circleservice.exception.CircleCreationException;
import com.stackroute.gupshup.circleservice.model.Circle;
import com.stackroute.gupshup.circleservice.service.CircleService;


@RestController
@RequestMapping(value="circle")
public class CircleController {

	@Autowired
	private CircleService circleService;
	
	@Autowired
	private LinkAssembler linkAssembler;

	//---------------get all circle-----------------
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Circle>> listAllCircles() {
		List<Circle> circle = null;
		try {

			if(circleService.findAllCircle()==null) {
				return new ResponseEntity<List<Circle>>(HttpStatus.NO_CONTENT);
			}
			else {
				circle = circleService.findAllCircle();
				linkAssembler.assembleLinksForCircleList(circle);
			}
		} catch (CircleCreationException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<List<Circle>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Circle>>(circle, HttpStatus.FOUND);
	}
	
	//---------create circle------------------------------
	//@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="",method=RequestMethod.POST)
	public ResponseEntity<Circle> saveCircle(@RequestBody Circle circle ) {
		
		System.out.println(circle);
		Circle circlesave = null;
		try {
			if(circle.getCircleName()==null) {
				return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
			}
			else {
//				circle.setCircleCreatedBy("hema");
//				circle.setCircleCreatedDate("19/06/2017");
//				circle.setKeywords(circle.getKeywords());
				circlesave=circleService.createCircle(circle); 
			}
		}

		catch(CircleCreationException circleCreationException){

			return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Circle>(circlesave, HttpStatus.CREATED);
	}
	//--------delete circle by id-----------------
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Circle> deleteCircle(@PathVariable String id)  {
		try {
			if(id==null) {
				return new ResponseEntity<Circle>(HttpStatus.NO_CONTENT);
			}
			else {
				circleService.deleteCircle(id);
			}
		} catch (CircleCreationException e) {
			return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Circle>(HttpStatus.OK);

	}
	//-------------------Retrieve Single circle--------------------------------------------------------

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Circle> getCircle(@PathVariable("id") String id) {

		Circle circle = null;
		try {

			if (circleService.findById(id) == null) {
				return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
			}
			else {
				circle = circleService.findById(id);
			}
		} catch (CircleCreationException e) {

			return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Circle>(circle, HttpStatus.OK);
	}   
	//---------update circle-----------------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Circle> updateCircle(@PathVariable("id") String id, @RequestBody Circle circle){
		
		try {
			circleService.updateCircle(circle);
			if (circleService.findById(id) == null) {
				return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
			}
			else {
				circleService.updateCircle(circle);
			}
		}
		catch (CircleCreationException e) {
			return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Circle>(circle, HttpStatus.OK);
	}

	//-----------------Delete all user------------------
	@RequestMapping(value = "/deleteall", method = RequestMethod.DELETE)
	public ResponseEntity<Circle> deleteAllCircle() {
		try {
			if(circleService.findAllCircle()!=null)	{
				circleService.deleteAllCircle();
			}
			else {
				return new ResponseEntity<Circle>(HttpStatus.BAD_REQUEST);
			}
		}
		catch (CircleCreationException e) {
			return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);

		}
		return new ResponseEntity<Circle>(HttpStatus.OK);
	}
	
}
