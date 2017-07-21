package com.stackroute.gupshup.recommedationservice.service.testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.stackroute.gupshup.recommendationservice.entity.CircleRecommendation;
import com.stackroute.gupshup.recommendationservice.exception.RecommendationException;
import com.stackroute.gupshup.recommendationservice.repository.CircleRecommendationRepository;
import com.stackroute.gupshup.recommendationservice.service.CircleRecommendationService;
import com.stackroute.gupshup.recommendationservice.service.CircleRecommendationServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CircleRecommendationServiceImpl.class)
public class CircleRecommendationServiceTests {

	@Mock
	private CircleRecommendationRepository circleRecommendationRepository;
	
	@InjectMocks
	private CircleRecommendationServiceImpl circleRecommendationServiceImpl;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void createCircleTest(){
		List<String> keywords = new ArrayList<String>();
		keywords.add("messaging app");
		keywords.add("circles");
		CircleRecommendation circleRecommendation=new CircleRecommendation();
		circleRecommendation.setCircleId("GU01");
		circleRecommendation.setCircleName("gupshup");
		circleRecommendation.setCreatedBy("teja");
		circleRecommendation.setKeywords(keywords);
		
		when(circleRecommendationRepository.createCircle(
				circleRecommendation.getCircleId(),
				circleRecommendation.getKeywords(),
				circleRecommendation.getCircleName(),
				circleRecommendation.getCreatedBy())).thenReturn((Map<String, Object>) circleRecommendation);
		
		Map<String, Object> circle = new HashMap<String, Object>();
		
		try {
			circle = circleRecommendationServiceImpl.createCircle(circleRecommendation);
		} catch (RecommendationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(circle.get("circleId"));
		assertEquals("GU01",circle.get("circleId") );
	}
	
	/*public void createCircleTest(){
	
	//when CircleRecommendationRepository.createCircle(,,,) called
	//return Circle
	
//various	circleRecommendatinService.createCircle(string a)
//	//CircleRecommendation circleRecommendation=new CircleRecommendation();
//	circleRecommendation.setCircleId("GU01");
//	circleRecommendation.setCircleName("gupshup");
//	circleRecommendation.setCreatedBy("teja");
//	ArrayList<String> keywords = new ArrayList<String>();
//	keywords.add("messaging app");
//	keywords.add("circles");
//	circleRecommendation.setKeywords(keywords);
//	System.out.println(circleRecommendation.getCircleId());
	
}*/
}
