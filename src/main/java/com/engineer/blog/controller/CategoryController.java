package com.engineer.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineer.blog.entity.Category;
import com.engineer.blog.payload.CategoryDto;
import com.engineer.blog.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	private CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService=categoryService;
	}
	//build add category REST API
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
		CategoryDto savedCategory=categoryService.addCategory(categoryDto);
		return new ResponseEntity<>(savedCategory,HttpStatus.CREATED);
	}
	@GetMapping("{id}")
	public ResponseEntity<CategoryDto>getCategory(@PathVariable("id") Long categoryId){
		CategoryDto categoryDto=categoryService.getCategory(categoryId);
		return ResponseEntity.ok(categoryDto);
	}
	@GetMapping
    public ResponseEntity<List<CategoryDto>>getAllCatogries(){
	List<CategoryDto> categoryDto=categoryService.getAllCategories();
	return ResponseEntity.ok(categoryDto);
}
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("{id}")
	public ResponseEntity<CategoryDto>updateCategory(CategoryDto categoryDto,
			@PathVariable("id")Long categoryId){
		return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
		
	}
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String>deleteCategory(@PathVariable("id") Long categoryId){
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok("Category deleted Successfully!.");
		
	}
}
