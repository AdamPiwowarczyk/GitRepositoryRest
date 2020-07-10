package com.restapi.gitRepository.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.gitRepository.entity.User;
import com.restapi.gitRepository.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@GetMapping("/{userName}")
	public User getUser(@PathVariable String userName){
		return userService.getUser(userName);
	}
	
	@PostMapping
	public User createUser(@RequestBody User userRequest) {
		return userService.createUser(userRequest);
	}
	
	@PutMapping("/{userName}")
	public User upadateUser(@PathVariable String userName, @RequestBody User userRequest) {
		return userService.upadateUser(userName, userRequest);
	}
	
	@DeleteMapping("/{userName}")
	public ResponseEntity<?> deleteUser(@PathVariable String userName){
		return userService.deleteUser(userName);
	}

}
