package com.restapi.gitRepository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restapi.gitRepository.entity.User;
import com.restapi.gitRepository.exception.DuplicateNameException;
import com.restapi.gitRepository.exception.ResourceNotFoundException;
import com.restapi.gitRepository.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

	public User getUser(String userName){
		return userRepository.findByName(userName)
				.orElseThrow(() -> new ResourceNotFoundException("Username \"" + userName + "\" not found"));
	}
	
	public User createUser(User userRequest) {
		if(userRepository.existsByName(userRequest.getName()))
			throw new DuplicateNameException("User with name \"" +  userRequest.getName() + "\" already exists");
		else
			return userRepository.save(userRequest);
	}
	
	@Transactional
	public User upadateUser(String userName, User userRequest) {
		return userRepository.findByName(userName).map(user ->{
			user.setName(userRequest.getName());
			return userRepository.save(user);
		}).orElseThrow(() -> new ResourceNotFoundException("Username \"" + userName + "\" not found"));
	}
	
	public ResponseEntity<?> deleteUser(String userName){
		return userRepository.findByName(userName).map(user ->{
			userRepository.delete(user);
			return ResponseEntity.ok().build();
		}).orElseThrow(()->new ResourceNotFoundException("Username \"" + userName + "\" not found"));
	}

	
	

}
