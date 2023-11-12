package com.blog.service;

import com.blog.paylaods.CommentDto;
import com.blog.paylaods.PostDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer postId);
	
	void deleteComment(Integer commentId);
	
}
