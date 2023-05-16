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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engineer.blog.payload.PostDto;
import com.engineer.blog.payload.PostResponse;
import com.engineer.blog.service.PostService;
import com.engineer.blog.utils.AppConstants;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/posts")
public class PostController {

	private PostService postService;
	
	public PostController(PostService postService) {
		this.postService=postService;
		}
	//create post  
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto>createPost(@Valid @RequestBody PostDto postDto){
      
		return new ResponseEntity<>(postService.createPost(postDto),HttpStatus.CREATED);
	}
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value="pageNo",defaultValue=AppConstants.DEFAULT_PAGE_NUMBER,required=false)int pageNo,
			@RequestParam(value="pageSize",defaultValue=AppConstants.DEFAULT_PAGE_SIZE,required=false)int pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.DEFAULT_SORT_BY,required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.DEFAULT_SORT_DIRECTION,required=false)String sortDir){
	return postService.getAllPosts(pageNo,pageSize, sortBy,sortDir);
	}
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id){
		return ResponseEntity.ok(postService.getPostById(id));
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto>updatePost(@Valid @RequestBody PostDto postDto,@PathVariable(name="id") long id){
     PostDto postResponse=postService.updatePost(postDto, id);
		return new ResponseEntity<>(postResponse,HttpStatus.OK);
		
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String>deleteById(@PathVariable(name="id")long id){
	postService.deletePostById(id);
	return new ResponseEntity<>("Post id "+id+"is deleted successfully",HttpStatus.OK);
		
	}
	 @GetMapping("/category/{id}")
	    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId){
	        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
	        return ResponseEntity.ok(postDtos);
	    }
	
	
	
	
}