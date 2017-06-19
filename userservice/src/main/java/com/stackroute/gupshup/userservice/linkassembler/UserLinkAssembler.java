package com.stackroute.gupshup.userservice.linkassembler;

import com.stackroute.gupshup.userservice.domain.User;

/* UserLinkAssembler will add links */
public interface UserLinkAssembler {

	public User UserProfileLinks(User user);
	public User followUserLinks(User user);
}
