package com.stackroute.gupshup.circleservice.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.gupshup.circleservice.exception.CircleCreationException;
import com.stackroute.gupshup.circleservice.model.*;
import com.stackroute.gupshup.circleservice.repository.CircleRepository;


@Service
public class CircleServiceImpl implements CircleService {
	
	@Autowired
	private CircleRepository circleRepo;
	//-------- find all circle-----------
	@Override
	public List<Circle> findAllCircle() throws CircleCreationException {
		Circle circle = null;
		try {
			if(circle==null) {
			 throw new CircleCreationException("Cann't find circle");		
			}
			}
			catch(CircleCreationException circlecreationException)	{
				circlecreationException.getMessage();
			}
		return circleRepo.findAll();
	}
	//-------create circle---------------------------
	public Circle createCircle(Circle circle) throws CircleCreationException {
		try {
			if(circle.getCircleName()==null) {
				throw new CircleCreationException("Give Circle Name");		
			}
			else {
			circleRepo.save(circle);
			}
		}
		catch(CircleCreationException circlecreationException)	{
			circlecreationException.getMessage();
		}
		return circle;
	}
	//----------------find circle by id--------------------
	@Override
	public Circle findById(String id) throws CircleCreationException{
		 try {
		 if(id==null) {
			 throw new CircleCreationException("No Circle available");		
		 }
		}
		catch(CircleCreationException circlecreationException)	{
			circlecreationException.getMessage();
		}
		 return circleRepo.findOne(id);
	}
	//------------circle is exist--------------------------
	@Override
	public boolean ifCircleExist(Circle circle) throws CircleCreationException{
		try {
			String circleid = circle.getId().toString();
			if(findById(circleid).equals(circle)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(CircleCreationException circlecreationException)	{
			circlecreationException.getMessage();
		}
		return true;
	}
	//-----------------update circle-------------------------
	@Override
	public void updateCircle(Circle currentCircle) {
		try {
			if(currentCircle.getCircleName()!=null)	{
				circleRepo.save(currentCircle);
			}
			else {
				throw new CircleCreationException("Give Circle name");
			}
		}
		catch(CircleCreationException circlecreationException)	{
			circlecreationException.getMessage();
		}
		
	}
	//----------delete circle-----------------------
	@Override
	public void deleteCircle(String id) throws CircleCreationException {
		try {
			if(id!=null) {
				circleRepo.delete(id);
			}
			else {
				throw new CircleCreationException("Cann't delete circle");
			}
		}
		catch(CircleCreationException circlecreationException)	{
			circlecreationException.getMessage();
		}
	}
	//------------delete all circles------------------------
		@Override
		public void deleteAllCircle() throws CircleCreationException {
			try {
				if(circleRepo.findAll()!=null) {
					circleRepo.deleteAll();
				}
				else {
					throw new CircleCreationException("No circle Available");
				}
			}
			catch(CircleCreationException circlecreationException) {
				circlecreationException.getMessage();
			}
			
			
		}
	

	
}
