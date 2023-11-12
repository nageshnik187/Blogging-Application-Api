package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.paylaods.ApiResponse;
import com.blog.paylaods.CommentDto;
import com.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createCommentHandler(@RequestBody CommentDto commentDto, @PathVariable Integer postId) {
		
		System.out.println(commentDto.getCommentContent());
	
		CommentDto createdComment = this.commentService.createComment(commentDto, postId);		
		
		return new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteCommentHandler(@PathVariable Integer commentId) {
	
		this.commentService.deleteComment(commentId);		
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully !!!...",true), HttpStatus.OK);
		
	}
	
}
