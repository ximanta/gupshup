package com.stackroute.gupshup.circleservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.stackroute.gupshup.circleservice.model.Member;

public interface MemberRepository extends MongoRepository<Member, String>{

	
	@Query("{username:?0}")
	public List<Member> findAllCircle(String username);
	
	@Query("{circleId:?0}")
	public List<Member> findAllMemeber(String circleId);
	
	public Member findOneByCircleIdAndUsername(String circleId,String username);
}
