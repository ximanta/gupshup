package com.stackroute.gupshup.userservice.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.stackroute.gupshup.userservice.domain.User;

@SpringBootTest
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        //Arrange
        User user1 = new User();
        user1.setUserName("randeep18");
        user1.setFirstName("Randeep");
        user1.setLastName("Kaur");
        user1.setPassword("demo12345");
        user1.setGender("female");
        user1.setDob("10-10-1994");
        user1.setEmailId("randeep123@gmail.com");
        user1.setContactNo("9876543210");
        user1.setProfilePhoto("default.jpg");
        user1.setFollowingCount(0);
        user1.setFollowing(null);

        User user2 = new User();
        user2.setUserName("charu18");
        user2.setFirstName("Charu");
        user2.setLastName("Bhatt");
        user2.setPassword("demo12345");
        user2.setGender("female");
        user2.setDob("10-10-1994");
        user2.setEmailId("charu123@gmail.com");
        user2.setContactNo("9876543210");
        user2.setProfilePhoto("default.jpg");
        user2.setFollowingCount(0);
        user2.setFollowing(null);
        
        userRepository.save(user1);
        userRepository.save(user2);

    }
    
    
    @Test
    public void testDataFetch(){
        User user1 = userRepository.findOne("randeep18");
        assertNotNull(user1);
        assertEquals("Randeep", user1.getFirstName());
    }

}