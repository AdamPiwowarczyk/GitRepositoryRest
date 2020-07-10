package com.restapi.gitRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.restapi.gitRepository.entity.GitRepo;
import com.restapi.gitRepository.entity.User;
import com.restapi.gitRepository.repository.GitRepoRepository;
import com.restapi.gitRepository.repository.UserRepository;
import com.restapi.gitRepository.service.GitRepositoryService;
import com.restapi.gitRepository.service.UserService;

@SpringBootTest
class GitRepositoryServiceTest {
	@Autowired
	private GitRepoRepository gitRepoRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void getEmptyList() {			
		final UserService userService = new UserService(userRepository);
		final GitRepositoryService gitRepoService = new GitRepositoryService(gitRepoRepository, userRepository);
		User user1 = userService.createUser(new User());
		List<GitRepo> gitRepos = gitRepoService.getAllGitReposByUserName(user1.getName());
		assertTrue(gitRepos.isEmpty());
	}
	
	@Test
	public void CreateAndGetGitRepoTest() {			
		final UserService userService = new UserService(userRepository);
		final GitRepositoryService gitRepoService = new GitRepositoryService(gitRepoRepository, userRepository);
		User user1 = userService.createUser(new User());
		GitRepo gitRepo = gitRepoService.createGitRepo(user1.getName(), new GitRepo());
		GitRepo gitRepo2 = gitRepoService.getGitRepo(user1.getName(), gitRepo.getFullName());
		assertEquals(gitRepo.getFullName(), gitRepo2.getFullName());
	}
	
	@Test
	public void getAllGitReposByUserNameTest() {			
		final UserService userService = new UserService(userRepository);
		final GitRepositoryService gitRepoService = new GitRepositoryService(gitRepoRepository, userRepository);
		User user1 = userService.createUser(new User());

		GitRepo repo1 = new GitRepo();
		repo1.setFullName("first");
		GitRepo repo2 = new GitRepo();
		repo1.setFullName("second");
		GitRepo gitRepo = gitRepoService.createGitRepo(user1.getName(), repo1);
		GitRepo gitRepo2 = gitRepoService.createGitRepo(user1.getName(), repo2);
		List<GitRepo> gitRepos = gitRepoService.getAllGitReposByUserName(user1.getName());
		assertTrue(gitRepos.size() == 2);
	}
	
	@Test
	public void updateGitRepoTest() {			
		final UserService userService = new UserService(userRepository);
		final GitRepositoryService gitRepoService = new GitRepositoryService(gitRepoRepository, userRepository);
		User user1 = userService.createUser(new User());

		GitRepo gitRepo = gitRepoService.createGitRepo(user1.getName(), new GitRepo());
		GitRepo gitRepoRequest = new GitRepo();
		gitRepoRequest.setFullName("update");
		GitRepo gitRepo2 = gitRepoService.updateGitRepo(user1.getName(), gitRepo.getFullName(), gitRepoRequest);
		assertEquals(gitRepo2.getFullName(), gitRepoRequest.getFullName());
	}
	

	@Test
	public void deleteGitRepoTest() {			
		final UserService userService = new UserService(userRepository);
		final GitRepositoryService gitRepoService = new GitRepositoryService(gitRepoRepository, userRepository);
		User user1 = userService.createUser(new User());

		GitRepo gitRepo = gitRepoService.createGitRepo(user1.getName(), new GitRepo());
		gitRepoService.deleteGitRepo(user1.getName(), gitRepo.getFullName());
		List<GitRepo> repos = gitRepoService.getAllGitReposByUserName(user1.getName());
		assertTrue(repos.isEmpty());
	}
	
	
	
	@AfterEach
	public void cleanAfterTest() {
		this.gitRepoRepository.deleteAll();
		this.userRepository.deleteAll();
	}


}