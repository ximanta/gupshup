package com.stackroute.gupshup.activityproducer.domain;

import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActorObjectImplTest {
	
	@Test public void checkMyClassIsImmutable() {
	    assertImmutable(Person.class); 
	}

}
