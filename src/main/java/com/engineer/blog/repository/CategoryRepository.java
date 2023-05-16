package com.engineer.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.engineer.blog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
