package com.stackroute.gupshup.userservice.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.stackroute.gupshup.userservice.domain.User;
import com.stackroute.gupshup.userservice.linkassembler.UserLinkAssembler;
import com.stackroute.gupshup.userservice.service.UserService;

@SpringBootTest
@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
    private UserService userServiceMock;
    private UserController userController;
    
    @Before
    public void setUp() {
    	userServiceMock = Mockito.mock(UserService.class);
    	userController = new UserController();
		userController.setUserService(userServiceMock);
    }
    
	@MockBean
	private UserLinkAssembler userLinkAssembler;
    
    @Autowired
	MessageSource messageSource;
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Test
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
        when(userServiceMock.getUserByUserName("charu18")).thenReturn(user);
        
        mockMvc.perform(get("/user/charu18"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(expectedJsonResponse));
        verify(userServiceMock, times(1)).getUserByUserName("charu18");
        verifyNoMoreInteractions(userServiceMock);
    }
	
}