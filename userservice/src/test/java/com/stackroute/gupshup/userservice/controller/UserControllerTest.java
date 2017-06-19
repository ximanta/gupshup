package com.stackroute.gupshup.userservice.controller;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.userservice.domain.User;
import com.stackroute.gupshup.userservice.service.UserService;

import org.junit.Before;
import org.junit.Test;

//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.nio.charset.Charset;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@MockBean
    private UserService userService;
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
	@Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
	
	/*@Test
    public void getUserByUserName() throws Exception {
        User user = new User();
        user.setFirstName("Charu");
        user.setLastName("Bhatt");
        user.setUserName("charu18");
        user.setPassword("ch123");
        user.setEmailId("charubhatt40@gmail.com");
        user.setGender("female");
        user.setDob("18-10-1994");
        user.setContactNo("7042759279");
        String expectedJsonResponse="{\n" +
                "  \"firstName\": \"Charu\",\n" +
                "  \"lastName\": \"Bhatt\",\n" +
                "  \"UserName\": \"charu18\",\n" +
                "  \"emailId\": \"charubhatt40@gmail.com\"\n" +
                "  \"dob\": \"18-10-1994\"\n" +
                "  \"gender\": \"female\"\n" +
                "  \"contactNo\": \"7042759279\"\n" +
                "}";
        when(userService.getUserByUserName("charu18")).thenReturn(user);
        mockMvc.perform(get("/user/charu18"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(expectedJsonResponse));
        verify(userService, times(1)).getUserByUserName("charu18");
        verifyNoMoreInteractions(userService);
    }*/
	
	@Test
    public void deleteUser() throws Exception {
        String expectedJsonResponse="{\n" +
                "  \"message\": \"User deleted successsfully\"\n" +
                "}";
        

        doNothing().when(userService).deleteUser("5943c522e103597e5c996a88");
        mockMvc.perform(delete("/user/5943c522e103597e5c996a88")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(expectedJsonResponse));
        verify(userService, times(1)).deleteUser("5943c522e103597e5c996a88");
        verifyNoMoreInteractions(userService);
    }
	
    /*@Test
    public void saveMovie() throws Exception {
        Movie movie1 = new Movie();
        movie1.setImdbID("imdb1");
        movie1.setTitle("Meter");
        movie1.setYear("2015");
        movie1.setPoster("meter.jpg");
        when(movieService.saveMovie(movie1)).thenReturn(movie1);
        mockMvc.perform(post("/v1/api/movie")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(movie1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

        verify(movieService, times(1)).saveMovie(movie1);
        verifyNoMoreInteractions(movieService);
    }*/
	
/*	    public static String asJsonString(final Object obj) {
    		try {
        		final ObjectMapper mapper = new ObjectMapper();
        		final String jsonContent = mapper.writeValueAsString(obj);
        		System.out.println(jsonContent);
        		return jsonContent;
    		} catch (Exception e) {
        		throw new RuntimeException(e);
    	}

*/

}
