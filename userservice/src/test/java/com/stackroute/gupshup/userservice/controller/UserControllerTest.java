package com.stackroute.gupshup.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.gupshup.userservice.domain.User;
import com.stackroute.gupshup.userservice.service.UserService;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

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
    public void list() throws Exception {
    	User user1 = new User();
	    movie1.setImdbID("imdb1");
	    movie1.setTitle("Meter");
	    movie1.setPoster("meter.jpg");
	    movie1.setYear("2015");

	        Movie movie2 = new Movie();
	        movie2.setImdbID("imdb2");
	        movie2.setTitle("Gladiator");
	        movie2.setPoster("gladiator.jpg");
	        movie2.setYear("2010");
	        List<Movie> movies = Arrays.asList(
	                movie1,movie2);
	        when(movieService.listAllMovies()).thenReturn(movies);
	        mockMvc.perform(get("/v1/api/movie"))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	                .andExpect(jsonPath("$", hasSize(2)))
	                .andExpect(jsonPath("$[0].imdbID", is("imdb1")))
	                .andExpect(jsonPath("$[0].Title", is("Meter")))
	                .andExpect(jsonPath("$[1].Poster", is("gladiator.jpg")))
	                .andExpect(jsonPath("$[1].Year", is("2010")));
	       verify(movieService, times(1)).listAllMovies();
	       verifyNoMoreInteractions(movieService);

	    }
*/

	    @Test
	    public void getUserByUserName() throws Exception {
	        User user = new User();
	        user.setFirstName("Randeep");
	        user.setLastName("Kaur");
	        user.setUserName("randeep18");
	        user.setGender("female");
	        user.setDob("01-10-1994");
	        user.setEmailId("randeep123@gmail.com");
	        user.setContactNo("9876543210");
	        user.setProfilePhoto("defaultPhoto.jpg");
	        user.setFollowingCount(0);
	        user.setFollowing(null);
	        
	        String expectedJsonResponse="{\n" +
	        		"  \"_id\" : \"59450eaa1c4fa2294769a4f2\",\n" +
	                "  \"firstName\": \"Randeep\",\n" +
	                "  \"lastName\": \"Kaur\",\n" +
	                "  \"userName\": \"randeep18\",\n" +
	                "  \"gender\": \"female\"\n" +
	                "  \"dob\": \"01-10-1994\",\n" +
	                "  \"emailId\": \"randeep123@gmail.com\",\n" +
	                "  \"contactNo\": \"9876543210\",\n" +
	                "  \"profilePhoto\": \"defaultPhoto.jpg\"\n" +
	                "  \"followingCount\": \"0\",\n" +
	                "  \"following\": \"[]\",\n" +
	                "}";
	        when(userService.getUserByUserName("randeep18")).thenReturn(user);
	        mockMvc.perform(get("/user/randeep18"))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	                .andExpect(content().json(expectedJsonResponse));
	        verify(userService, times(1)).getUserByUserName("randeep18");
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

	    @Test
	    public void deleteUser() throws Exception {
	        String expectedJsonResponse="{\n" +
	                "  \"message\": \"User deleted successsfully\"\n" +
	                "}";

	        doNothing().when(userService).deleteUser("59450eaa1c4fa2294769a4f2");
	        mockMvc.perform(delete("/user/59450eaa1c4fa2294769a4f2")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	                .andExpect(content().json(expectedJsonResponse));
	        verify(userService, times(1)).deleteUser("randeep18");
	        verifyNoMoreInteractions(userService);
	    }

/*	    public static String asJsonString(final Object obj) {
	        try {
	            final ObjectMapper mapper = new ObjectMapper();
	            final String jsonContent = mapper.writeValueAsString(obj);
	            System.out.println(jsonContent);
	            return jsonContent;
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
	
	*/
	
	
}
