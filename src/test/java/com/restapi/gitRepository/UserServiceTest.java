package com.restapi.gitRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.restapi.gitRepository.entity.User;
import com.restapi.gitRepository.repository.UserRepository;
import com.restapi.gitRepository.service.UserService;

@SpringBootTest
class UserServiceTest {


	@Autowired
	UserRepository userRepository;
	
	
	@Test
	public void getEmptyList() {	
		final UserService service = new UserService(userRepository);
		List<User> users = service.getAllUsers();
		assertTrue(users.isEmpty());
	}
	
	@Test
	public void GetAllUsersTest() {	
		final UserService service = new UserService(userRepository);
		User user1 = new User();
		User user2 = new User();
		user1.setName("user1");
		user2.setName("user2");
		service.createUser(user1);
		service.createUser(user2);
		List<User> users = service.getAllUsers();
		assertTrue(users.size() == 2);
	}
	
	@Test
	public void CreateAndGetUserTest() {	
		final UserService service = new UserService(userRepository);
		User user1 = service.createUser(new User());
		User user2 = service.getUser(user1.getName());
		assertEquals(user1.getName(), user2.getName());
	}
	
	@Test
	public void UpdateUserTest() {	
		final UserService service = new UserService(userRepository);
		User user1 = service.createUser(new User());
		User user2 = new User();
		user2.setName("user2");
		user1 = service.upadateUser(user1.getName(), user2);
		assertEquals(user1.getName(), user2.getName());
	}
	
	
	@Test
	public void deleteUserTest() {	
		final UserService service = new UserService(userRepository);
		User user = service.createUser(new User());
		service.deleteUser(user.getName());
		List<User> users = service.getAllUsers();
		assertTrue(users.isEmpty());
	}
	
	@AfterEach
	public void cleanAfterTest() {
		this.userRepository.deleteAll();
	}

}
