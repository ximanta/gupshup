package com.stackroute.gupshup.circleservice.controller;


import java.util.List;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stackroute.gupshup.circleservice.controller.hateoas.LinkAssembler;
import com.stackroute.gupshup.circleservice.exception.CircleException;
import com.stackroute.gupshup.circleservice.model.Circle;
import com.stackroute.gupshup.circleservice.service.CircleService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value="circle")
public class CircleController {

	//---------------------------Autowired Circle service------------------
	@Autowired
	private CircleService circleService;

	//---------------------------Autowired link assembler-----------------
	@Autowired
	private LinkAssembler linkAssembler;

	//---------------------------Get all circle---------------------------
	@ApiOperation(value="List All Circles", notes="list all circles")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Circle>> listAllCircles() {
		List<Circle> circlelist = null;
		try {
			if(circleService.findAllCircles()==null) {
				return new ResponseEntity<List<Circle>>(circlelist, HttpStatus.NO_CONTENT);
			}
			else {
				circlelist = circleService.findAllCircles();
				linkAssembler.assembleLinksForCircleList(circlelist);
			}
		} catch (CircleException e) {
			return new ResponseEntity<List<Circle>>(circlelist, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Circle>>(circlelist, HttpStatus.FOUND);
	}

	//----------------------------Create circle------------------------------
	@ApiOperation(value="Save Circle", notes="Save circle details ")
	@RequestMapping(value="",method=RequestMethod.POST)
	public ResponseEntity<Circle> saveCircle(@Valid @RequestBody Circle circle, BindingResult result ) {
		Circle circlesave = null;
		if(result.hasErrors()){
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
		try {
			if(circle.getCircleName()==null) {
				return new ResponseEntity<Circle>(circlesave,HttpStatus.BAD_REQUEST);
			}
			else {
				circlesave=circleService.createCircle(circle);
			}
		} catch(CircleException circleCreationException){
			return new ResponseEntity<Circle>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new ResponseEntity<Circle>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Circle>(circlesave, HttpStatus.CREATED);
	}

	//----------------------------Delete circle by id-----------------
	@ApiOperation(value="Delete Circle", notes="circle details deleted")
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Circle> deleteCircle(@PathVariable String id)  {
		try {
			if(id==null) {
				return new ResponseEntity<Circle>(HttpStatus.NO_CONTENT);
			}
			else {
				circleService.deleteCircle(id);
			}
		} catch (CircleException e) {
			return new ResponseEntity<Circle>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new ResponseEntity<Circle>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Circle>(HttpStatus.OK);

	}

	//----------------------------Retrieve Single circle--------------------------------------------------------
	@ApiOperation(value="Get One Circle detail", notes="get circle details")
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
		} catch (CircleException e) {

			return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Circle>(circle, HttpStatus.OK);
	}   

	//----------------------------Update circle-----------------------------
	@ApiOperation(value="Update Circle", notes="update circle details")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Circle> updateCircle(@PathVariable String id, @RequestBody Circle circle){

		try {
			if (circle.getCircleName() == null) {
				return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
			}
			else {
				circleService.updateCircle(circle);
			}
		}
		catch (CircleException e) {
			return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Circle>(circle, HttpStatus.OK);
	}

	//------------------------------Delete all user------------------
	@ApiOperation(value="Delete All Circle", notes="delete all circle details")
	@RequestMapping(value = "/deleteall", method = RequestMethod.DELETE)
	public ResponseEntity<Circle> deleteAllCircle() {
		try {
			if(circleService.findAllCircles()!=null)	{
				circleService.deleteAllCircle();
			}
			else {
				return new ResponseEntity<Circle>(HttpStatus.BAD_REQUEST);
			}
		}
		catch (CircleException e) {
			return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Circle>(HttpStatus.OK);
	}

}
