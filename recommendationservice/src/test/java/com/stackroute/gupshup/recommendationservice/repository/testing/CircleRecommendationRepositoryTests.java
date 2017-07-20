/*package com.stackroute.gupshup.recommendationservice.repository.testing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.stackroute.gupshup.recommendationservice.entity.CircleRecommendation;
import com.stackroute.gupshup.recommendationservice.repository.CircleRecommendationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CircleRecommendationRepository.class)
public class CircleRecommendationRepositoryTests {
	
	private MockMvc mockMvc;
	
	@Autowired
	WebApplicationContext wac;
	
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}
	
	@Test
	public void createCircleTest(){
		CircleRecommendation circleRecommendation=new CircleRecommendation();
		circleRecommendation.setCircleId("GU01");
		circleRecommendation.setCircleName("gupshup");
		circleRecommendation.setCreatedBy("teja");
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("messaging app");
		keywords.add("circles");
		circleRecommendation.setKeywords(keywords);
		System.out.println(circleRecommendation.toString());
		Map<String, Object> cir = new HashMap<String, Object>();
		cir.put("circle", circleRecommendation);
		Map<String, Object> circle = circleRecommendationRepository.createCircle(
				circleRecommendation.getCircleId(), 
				circleRecommendation.getKeywords(), 
				circleRecommendation.getCircleName(), 
				circleRecommendation.getCreatedBy());
		System.out.println(circle.size());
		System.out.println(cir.get("circle"));
		assertEquals(cir.get("circle"), circle.get("circle"));
	}
	
}
*/