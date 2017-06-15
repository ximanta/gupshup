package com.stackroute.gupshup.circleservice.controller.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.stackroute.gupshup.circleservice.controller.CircleController;
import com.stackroute.gupshup.circleservice.controller.UserController;
import com.stackroute.gupshup.circleservice.model.Circle;
import com.stackroute.gupshup.circleservice.model.User;
@Component
public class LinkAssemblerImpl implements LinkAssembler {

	//----------create link for circle bean---------------
    @Override
   public Iterable<Circle> assembleLinksForCircleList(List<Circle> circle)
   {
       for(Circle circle1:circle)
       {    
           Link selfLink=linkTo(CircleController.class).slash(circle1.getCircleId()).withSelfRel();
           circle1.add(selfLink);
            Link updateLink=linkTo(CircleController.class).slash(circle1.getCircleId()).withRel("update");
               circle1.add(updateLink);
               Link deleteLink=linkTo(CircleController.class).slash(circle1.getCircleId()).withRel("delete");
               circle1.add(deleteLink);
       }
       return circle;
   }
    //--------create link for user bean------------
	@Override
	public Iterable<User> assembleLinksForUserList(List<User> user) {
		 for(User user1:user)
	       {    
	           Link selfLink=linkTo(UserController.class).slash(user1.getPersonId()).withSelfRel();
	           user1.add(selfLink);
	            Link updateLink=linkTo(UserController.class).slash(user1.getPersonId()).withRel("update");
	            user1.add(updateLink);
	               Link deleteLink=linkTo(UserController.class).slash(user1.getPersonId()).withRel("delete");
	               user1.add(deleteLink);
	       }
	       return user;
	}

}