package com.blog.service.impl;

import java.lang.System.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.paylaods.CommentDto;
import com.blog.repository.CommentRepo;
import com.blog.repository.PostRepo;
import com.blog.service.CommentService;
import com.blog.service.PostService;

@Service	 
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	PostService postService;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	PostRepo postRepo;
	
	@Autowired
	CommentRepo CommentRepo;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "POstId", postId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		
		Comment savedComment = this.CommentRepo.save(comment);
		
		System.out.println(savedComment.toString());
		
		
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment = this.CommentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "CommentId", commentId));
		this.CommentRepo.delete(comment);
		
	}

}
