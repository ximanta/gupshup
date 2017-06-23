package com.stackroute.gupshup.userservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
@SpringApplicationConfiguration(classes = UserserviceApplication.class)
@WebAppConfiguration
//@EnableDiscoveryClient
public class UserserviceApplicationTests {

	@Test
	public void contextLoads() {
	}

}
