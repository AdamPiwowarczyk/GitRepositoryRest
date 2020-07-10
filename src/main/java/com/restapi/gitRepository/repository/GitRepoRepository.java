package com.restapi.gitRepository.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.gitRepository.entity.GitRepo;

public interface GitRepoRepository extends JpaRepository<GitRepo, Long> {
	Optional<GitRepo> findByFullNameAndOwnerName(String fullName, String userName);
	List<GitRepo>  findByOwnerName(String userName);
	Optional<GitRepo> findByFullName(String name);
	boolean existsByFullName(String name);
	boolean existsByFullNameAndOwnerName(String fullName, String userName);
}
