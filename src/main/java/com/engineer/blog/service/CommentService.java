package com.engineer.blog.service;

import java.util.List;


import com.engineer.blog.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(Long postId,CommentDto commentRequest);
	List<CommentDto> getCommentsByPostId(Long postId);
	CommentDto updateComment(Long postId, Long commentId,CommentDto commentRequest);
	CommentDto getCommentById(Long postId,Long commentid);
	void deleteComment(Long postId, Long commentId);
	
}
