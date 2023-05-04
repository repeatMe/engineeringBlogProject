package com.engineer.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.engineer.blog.entity.Post;

public interface PostRepository extends JpaRepository<Post,Long>{
	
	}
