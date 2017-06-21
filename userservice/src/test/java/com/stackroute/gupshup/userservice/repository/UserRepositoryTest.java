package com.stackroute.gupshup.userservice.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stackroute.gupshup.userservice.domain.User;

public class UserRepositoryTest {
	
	private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*@Before
    public void setUp() throws Exception {
        //Arrange
        Movie movie1 = new Movie();
        movie1.setImdbID("imdb1");
        movie1.setTitle("Meter");
        movie1.setYear("2015");
        movie1.setPoster("meter.jpg");


        Movie movie2 = new Movie();
        movie2.setImdbID("imdb2");
        movie2.setTitle("Gladiator");
        movie2.setPoster("gladiator.jpg");
        movie2.setYear("2010");

        assertNull(movie1.get_id());
        assertNull(movie2.get_id());//Should be null before save
        //Act
        this.movieRepository.save(movie1);
        this.movieRepository.save(movie2);
        //Assert
        assertNotNull(movie1.get_id());
        assertNotNull(movie2.get_id());
    }
*/    
    
    /*Test data retrieval*/
    @Test
    public void testDataFetch(){
        /*ACT*/
        User user = userRepository.findOne("randeep18");
        /*Assert*/
        assertNotNull(user);
        assertEquals("Randeep", user.getFirstName());
        
    }
    
    
    /*Test update*/
    @Test
    public void testDataUpdate(){
       /*Act*/
        User user2 = userRepository.findOne("randeep18");
        user2.setEmailId("randeep11@gmail.com");
        userRepository.save(user2);
        User tempUser= userRepository.findOne("randeep18");
        /*Assert*/
        assertNotNull(tempUser);
        assertEquals("randeep11@gmail.com", tempUser.getEmailId());
    }

    /*Test delete*/
    @Test
    public void testDataDelete(){
       /*Act*/
        User user2 = userRepository.findOne("randeep18");
        userRepository.delete(user2);
        User tempUser= userRepository.findOne("randeep18");
        /*Assert*/
        assertNull(tempUser);
      }

    @After
    public void tearDown() throws Exception {
        this.userRepository.deleteAll();
    }

}
