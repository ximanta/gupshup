package com.stackroute.gupshup.authserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stackroute.gupshup.authserver.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);
}
