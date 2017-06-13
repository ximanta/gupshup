package com.stackroute.gupshup.activitystreamservice.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityObjectImplTest {
	
	@Test public void checkMyClassIsImmutable() {
	    assertImmutable(ActivityObjectImpl.class); 
	}

}
