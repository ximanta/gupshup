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
		
		
		try{
			
			 throw new CircleCreationException("");		
			
			}
			catch(CircleCreationException circlecreationException)
			{
				circlecreationException.getMessage();
			}
		return circleRepo.findAll();
	}
	//-------create circle---------------------------
	public Circle createCircle(Circle circle) throws CircleCreationException {
		
		try{
			circleRepo.save(circle);
		 throw new CircleCreationException("");		
		
		}
		catch(CircleCreationException circlecreationException)
		{
			circlecreationException.getMessage();
		}
		return circle;
	}
	//----------------find circle by id--------------------
	@Override
	public Circle findById(String id) throws CircleCreationException{
		 try{
		 
		 throw new CircleCreationException("");		
		
		}
		catch(CircleCreationException circlecreationException)
		{
			circlecreationException.getMessage();
		}
		 return circleRepo.findOne(id);
	}
	//------------circle is exist--------------------------
	@Override
	public boolean ifCircleExist(Circle circle) throws CircleCreationException{
		try{
			String circleid = circle.getId().toString();
			
			if(findById(circleid).equals(circle))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(CircleCreationException circlecreationException)
		{
			circlecreationException.getMessage();
		}
		return true;
	}
	//-----------------update circle-------------------------
	@Override
	public void updateCircle(Circle currentCircle) {
		try{
			circleRepo.save(currentCircle);
			throw new CircleCreationException("");
		}
		catch(CircleCreationException circlecreationException)
		{
			circlecreationException.getMessage();
		}
		
	}
	//----------delete circle-----------------------
	@Override
	public void deleteCircle(String id) throws CircleCreationException {
		try{
			circleRepo.delete(id);
			throw new CircleCreationException("");
		}
		catch(CircleCreationException circlecreationException)
		{
			circlecreationException.getMessage();
		}
	}
	//------------delete all circles------------------------
		@Override
		public void deleteAllCircle() throws CircleCreationException {
			try{
				circleRepo.deleteAll();
				throw new CircleCreationException("");
			}
			catch(CircleCreationException circlecreationException)
			{
				circlecreationException.getMessage();
			}
			
			
		}
	

	
}
