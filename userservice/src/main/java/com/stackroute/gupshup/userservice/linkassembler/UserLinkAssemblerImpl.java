package com.stackroute.gupshup.userservice.linkassembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.stackroute.gupshup.userservice.controller.UserController;
import com.stackroute.gupshup.userservice.domain.User;

@Component
public class UserLinkAssemblerImpl implements UserLinkAssembler {

	/* hateoas links for the new user */
	@Override
	public User UserProfileLinks(User user) {
		/*Link selfLink = linkTo(UserController.class).slash(user.get_id()).withSelfRel();
		user.add(selfLink);
		*/
		Link updateLink = linkTo(UserController.class).slash(user.get_id()).withRel("updateProfile");
		user.add(updateLink);
		
		Link deleteLink = linkTo(UserController.class).slash(user.get_id()).withRel("deleteProfile");
		user.add(deleteLink);
		
		return user;
	}

	/* hateoas links for getUserByUserName */
	@Override
	public User followUserLinks(User user) {
		// TODO Auto-generated method stub
		Link followLink = linkTo(UserController.class).slash(user.get_id()).withRel("Follow");
		user.add(followLink);
		
		return user;
	}
}
