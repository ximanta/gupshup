package com.stackroute.gupshup.userservice.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.gupshup.userservice.UserserviceApplication;
import com.stackroute.gupshup.userservice.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes=UserserviceApplication.class)
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

        assertNull(user1.get_id());
        assertNull(user2.get_id());//Should be null before save
        //Act
        userRepository.save(user1);
        userRepository.save(user2);
        //Assert
        assertNotNull(user1.get_id());
        assertNotNull(user2.get_id());
    }
    
    
    /*Test data retrieval*/
    @Test
    public void testDataFetch(){
        /*ACT*/
        User user1 = userRepository.findOne("randeep18");
        /*Assert*/
        assertNotNull(user1);
        assertEquals("Randeep", user1.getFirstName());
    }
    
    
    /*Test update*/
    @Test
    public void testDataUpdate(){
       /*Act*/
        User user2 = userRepository.findOne("charu18");
        user2.setContactNo("9988776655");;
        userRepository.save(user2);
        User tempUser= userRepository.findOne("charu18");
        /*Assert*/
        assertNotNull(tempUser);
        assertEquals("9988776655", tempUser.getContactNo());
    }

    /*Test delete*/
//    @Test
//    public void testDataDelete(){
//       /*Act*/
//        User user2 = userRepository.findOne("randeep18");
//        userRepository.delete(user2);
//        User tempUser = userRepository.findOne("randeep18");
        /*Assert*/
//        assertNull(tempUser);
//      }

//    @After
//    public void tearDown() throws Exception {
//        this.userRepository.deleteAll();
//    }

}