package com.restapi.gitRepository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restapi.gitRepository.entity.GitRepo;
import com.restapi.gitRepository.exception.DuplicateNameException;
import com.restapi.gitRepository.exception.ResourceNotFoundException;
import com.restapi.gitRepository.repository.GitRepoRepository;
import com.restapi.gitRepository.repository.UserRepository;

@Service
public class GitRepositoryService {

	private GitRepoRepository gitRepoRepository;
	private UserRepository userRepository;

	@Autowired	
	public GitRepositoryService(GitRepoRepository gitRepoRepository, UserRepository userRepository) {
		this.gitRepoRepository = gitRepoRepository;
		this.userRepository = userRepository;
	}

	public List<GitRepo> getAllGitReposByUserName(String userName){
		return gitRepoRepository.findByOwnerName(userName);
	}

	public GitRepo getGitRepo(String userName, String gitRepoFullName){
		return gitRepoRepository.findByFullNameAndOwnerName(gitRepoFullName, userName)
				.orElseThrow(() -> new ResourceNotFoundException("Git Repo name \"" + gitRepoFullName + "\" or username \"" + userName + "\" not found"));
	}

	@Transactional
	public GitRepo createGitRepo(String userName, GitRepo gitRepoRequest) {
		String gitRepoRequestName = gitRepoRequest.getFullName();
		if(gitRepoRepository.existsByFullNameAndOwnerName(gitRepoRequestName, userName))
			throw new DuplicateNameException("Git Repo with name \"" + gitRepoRequestName + "\" already exists");		
		return userRepository.findByName(userName).map(user -> {
			gitRepoRequest.setOwner(user);
			return gitRepoRepository.save(gitRepoRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("Username \"" + userName + "\" not found"));
	}

	@Transactional
	public GitRepo updateGitRepo(String userName, String gitRepoFullName, GitRepo gitRepoRequest) {
		if(!userRepository.existsByName(userName)) {
			throw new ResourceNotFoundException("Username \"" + userName+ "\" not found");
		}
		if(gitRepoRepository.existsByFullName(gitRepoRequest.getFullName()))
			throw new DuplicateNameException("Git Repo with name \"" + gitRepoRequest.getFullName() + "\" already exists");		
		return gitRepoRepository.findByFullName(gitRepoFullName).map(gitRepo->{
			gitRepo.setFullName(gitRepoRequest.getFullName());
			gitRepo.setCloneUrl(gitRepoRequest.getCloneUrl());
			gitRepo.setDescription(gitRepoRequest.getDescription());
			gitRepo.setStars(gitRepoRequest.getStars());
			return gitRepoRepository.save(gitRepo);
		}).orElseThrow(()-> new ResourceNotFoundException("Git Repo name \"" + gitRepoFullName + "\" not found"));
	}
	
	public ResponseEntity<?> deleteGitRepo(String userName, String gitRepoFullName){
		return gitRepoRepository.findByFullNameAndOwnerName(gitRepoFullName, userName).map(gitRepo -> {
			gitRepoRepository.delete(gitRepo);
			return ResponseEntity.ok().build();
		}).orElseThrow(()-> new ResourceNotFoundException("Git Repo name \"" + gitRepoFullName + "\" or username \"" + userName + "\" not found"));
	}
	

}
