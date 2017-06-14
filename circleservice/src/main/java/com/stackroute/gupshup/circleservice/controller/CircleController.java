package com.stackroute.gupshup.circleservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.gupshup.circleservice.controller.hateoas.LinkAssembler;
import com.stackroute.gupshup.circleservice.exception.CircleCreationException;
import com.stackroute.gupshup.circleservice.model.*;
import com.stackroute.gupshup.circleservice.service.CircleService;


@RestController
@RequestMapping("/circle")
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
				circle = circleService.findAllCircle();
				linkAssembler.assembleLinksForCircleList(circle);
				if(circle.isEmpty()){
		            return new ResponseEntity<List<Circle>>(HttpStatus.NO_CONTENT);
		            }
			} catch (CircleCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        return new ResponseEntity<List<Circle>>(circle, HttpStatus.OK);
	    }
		//---------create circle------------------------------
	    @RequestMapping(value="",method=RequestMethod.POST)
	    public ResponseEntity<Circle> saveCircle(@RequestBody Circle circle )
	    {
	    	Circle circlesave = null;
	    	try{
	    	circlesave=circleService.createCircle(circle);   
	    	}
	    	 
	    	 catch(CircleCreationException circleCreationException){
	    		 circleCreationException.getMessage();
	    	 }
	    	 
	        return new ResponseEntity<Circle>(circlesave, HttpStatus.OK);
	    }
	    //--------delete circle by id-----------------
	    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	    public ResponseEntity deleteCircle(@PathVariable String id)
	    {
		 Map msgMap = new HashMap<String,String>();
	        msgMap.put("message","Circle deleted successsfully");
	    	try {
				circleService.deleteCircle(id);
				
		        } catch (CircleCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	 return new ResponseEntity<Map<String,String>>(msgMap, HttpStatus.OK);

	    }
 //-------------------Retrieve Single circle--------------------------------------------------------
	    
	    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Circle> getCircle(@PathVariable("id") String id){
	       
	        Circle circle = null;
			try {
				circle = circleService.findById(id);
				 if (circle == null) {
//			            System.out.println("User with id " + id + " not found");
			            return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
			        }
			} catch (CircleCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
	        return new ResponseEntity<Circle>(circle, HttpStatus.OK);
	    }   
	    //---------update circle-----------------------------
	    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	    public ResponseEntity<Circle> updateCircle(@PathVariable("id") String id, @RequestBody Circle circle){
	       // System.out.println("Updating User " + id);
	    	 Circle currentCircle = null;
	    	try{
	       
	    		currentCircle = circleService.findById(id);
	        if (currentCircle==null) {
//	            System.out.println("User with id " + id + " not found");
	            return new ResponseEntity<Circle>(HttpStatus.NOT_FOUND);
	        }

	        currentCircle.setCircleName(circle.getCircleName());
	        currentCircle.setCircleMembers(circle.getCircleMembers());
	        currentCircle.setCircleDescription(circle.getCircleDescription());
	        
	        circleService.updateCircle(currentCircle);
	        
	        }
	        catch (CircleCreationException e) {
				// TODO Auto-generated catch block
				e.getMessage();
	        }
	        return new ResponseEntity<Circle>(currentCircle, HttpStatus.OK);
	    }
	    //-----------------Delete all user------------------
		 @RequestMapping(value = "/deleteall", method = RequestMethod.DELETE)
		    public ResponseEntity<Circle> deleteAllCircle() {
		        try{
		        circleService.deleteAllCircle();
		        }
		        catch (CircleCreationException e) {
					// TODO Auto-generated catch block
					e.getMessage();
				}
		        return new ResponseEntity<Circle>(HttpStatus.OK);
		    }
}
