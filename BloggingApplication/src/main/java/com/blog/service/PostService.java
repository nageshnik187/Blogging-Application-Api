package com.blog.service;

import java.util.List;

import org.springframework.data.annotation.CreatedBy;

import com.blog.entity.Post;
import com.blog.paylaods.PostDto;
import com.blog.paylaods.PostResponse;

public interface PostService {

	//Create Post
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//Update Post
	public PostDto updatePostById(PostDto postDto, Integer postId);
	
	//Delete Post
	public void deletePostById(Integer postId);
	
	//Get Post 
	public PostDto getPostById(Integer postId);
	
	//Get All Post
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
//	
//	//Get All Post by user sorted
//	public PostResponse getAllPostByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
//		
//	//Get All Post by category sorted
//	public PostResponse getAllPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//Get Post By User
	public List<PostDto> getPostByUser(Integer UserId);
	
	//Get Post by Category
	public List<PostDto> getPostByCategory(Integer categoryId);
	
	//Search post by keyword
	public List<PostDto> searchPost(String keyword);
}
