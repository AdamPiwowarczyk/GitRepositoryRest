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

import com.restapi.gitRepository.entity.GitRepo;
import com.restapi.gitRepository.service.GitRepositoryService;

@RestController
@RequestMapping("/repositories")
public class RepositoryController {

	private final GitRepositoryService gitRepoService;
	
	@Autowired
	public RepositoryController(GitRepositoryService gitRepoService) {
		this.gitRepoService = gitRepoService;
	}

	@GetMapping("/{userName}/gitRepos")
	public List<GitRepo> getAllGitReposByUserName(@PathVariable String userName){
		return gitRepoService.getAllGitReposByUserName(userName);
	}
	@GetMapping("/{userName}/{gitRepoFullName}")
	public GitRepo getGitRepo(@PathVariable String userName, @PathVariable String gitRepoFullName){
		return gitRepoService.getGitRepo(userName, gitRepoFullName);
	}
	
	@PostMapping("/{userName}/gitRepos")
	public GitRepo createGitRepo(@PathVariable String userName, @RequestBody GitRepo gitRepoRequest) {
		return gitRepoService.createGitRepo(userName, gitRepoRequest);
	}
	
	@PutMapping("/{userName}/{gitRepoFullName}")
	public GitRepo updateGitRepo(@PathVariable String userName, @PathVariable String gitRepoFullName,
			@RequestBody GitRepo gitRepoRequest) {
		return gitRepoService.updateGitRepo(userName, gitRepoFullName, gitRepoRequest);
	}
	
	@DeleteMapping("/{userName}/{gitRepoFullName}")
	public ResponseEntity<?> deleteGitRepo(@PathVariable String userName, @PathVariable String gitRepoFullName){
		return gitRepoService.deleteGitRepo(userName, gitRepoFullName);
	}

	
	

}
