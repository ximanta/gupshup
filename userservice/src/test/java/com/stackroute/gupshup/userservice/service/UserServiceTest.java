package com.stackroute.gupshup.userservice.service;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.gupshup.userservice.domain.User;
import com.stackroute.gupshup.userservice.repository.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

	private UserService userService;
	private UserRepository userRepositoryMock;
	
	@Before
    public void setUp() {
		userRepositoryMock = Mockito.mock(UserRepository.class);
		userService = new UserServiceImpl();
		((UserServiceImpl)userService).setUserRepository(userRepositoryMock);
    }
	
	@Test
    public void getUserByUserName() throws Exception {
		when(userRepositoryMock.findOne(eq("Foo"))).thenReturn(null);
		User user = userService.getUserByUserName("Foo"); 
		assertNull(user);
	}
	
}
