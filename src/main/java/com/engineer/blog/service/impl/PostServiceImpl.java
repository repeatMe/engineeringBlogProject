	package com.engineer.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.ResourceClosedException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.engineer.blog.entity.Category;
import com.engineer.blog.entity.Post;
import com.engineer.blog.exception.ResourceNotFoundException;
import com.engineer.blog.payload.PostDto;
import com.engineer.blog.payload.PostResponse;
import com.engineer.blog.repository.CategoryRepository;
import com.engineer.blog.repository.PostRepository;
import com.engineer.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private ModelMapper mapper;
	private CategoryRepository categoryRepository;
	public PostServiceImpl(PostRepository postRepository,ModelMapper mapper,CategoryRepository categoryRepository) {
		this.postRepository=postRepository;
		this.mapper=mapper;
		this.categoryRepository=categoryRepository;
	}
	
	@Override
	public PostDto createPost(PostDto postDto) {
		Category category=categoryRepository.findById(postDto.getCategoryId())
		.orElseThrow(()->new ResourceNotFoundException
				("Category","id",postDto.getCategoryId()));
		
		//convert dto to entity
	Post post=mapToEntity(postDto);
	post.setCategory(category);
	Post newPost=postRepository.save(post);
		
	//convert entity to dto
	  PostDto postResponse = mapToDTO(newPost);
      return postResponse;
		}

	@Override
	public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {
		//sort	
		Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        //create pageable instance
    	Pageable pageable=PageRequest.of(pageNo, pageSize,sort);
    	
    	Page<Post>posts=postRepository.findAll(pageable);
		
   	//get content from page objects
		List<Post> listOfposts= posts.getContent();
		List<PostDto> content=  listOfposts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElement(posts.getTotalElements());
    	postResponse.setTotalPages(posts.getTotalPages());
    	postResponse.setLast(posts.isLast());
       	return postResponse;
	
	}
	@Override
	public PostDto getPostById(long id) {
	Post post=	postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
	return mapToDTO(post);
	}
	
	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		Category category=categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(()->new ResourceNotFoundException
						("Category","id",postDto.getCategoryId()));
		
		Post post=	postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		post.setCategory(category);
		Post updatedPost=postRepository.save(post);
		return mapToDTO(updatedPost);
	
	}
	@Override
	public void deletePostById(@PathVariable(name="id") long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
	}
	@Override
	public List<PostDto> getPostsByCategory(Long categoryId) {
	Category category=categoryRepository.findById(categoryId)
		.orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
	
		List<Post>posts=postRepository.findByCategoryId(categoryId);
	return posts.stream().map((post)->mapToDTO(post))
			.collect(Collectors.toList());			
	}
	
	// convert Entity into DTO
    private PostDto mapToDTO(Post post){
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
    	PostDto postDto=mapper.map(post, PostDto.class);
        return postDto;
    }
    // convert DTO to entity
    private Post mapToEntity(PostDto postDto){
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
    	Post post=mapper.map(postDto, Post.class);
        return post;
    }

	
	
}
