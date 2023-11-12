package com.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.hibernate.cfg.annotations.Nullability;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.OffsetMapping.Sort;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.paylaods.PostDto;
import com.blog.paylaods.PostResponse;
import com.blog.repository.CategoryRepo;
import com.blog.repository.PostRepo;
import com.blog.repository.UserRepo;
import com.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	PostRepo postRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "UserId", userId));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setPostImage("Default.png");
		post.setPostDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post savedPost = this.postRepo.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
		 
	}

	@Override
	public PostDto updatePostById(PostDto postDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "PostId", postId));
		post.setPostTitle(postDto.getPostTitle());
		post.setPostContent(postDto.getPostContent());
		post.setPostDate(new Date());
		
		Post updatedPost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "PostId", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "PostId", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortby, String sortDir) {
		
		org.springframework.data.domain.Sort sort = null; 
		sort = sortDir.equalsIgnoreCase("asc")?org.springframework.data.domain.Sort.by(sortby).ascending():org.springframework.data.domain.Sort.by(sortby).descending();
		
		/*if(sortDir.equalsIgnoreCase("asc")) {
			sort = org.springframework.data.domain.Sort.by(sortby).ascending();
		}else {
			sort = org.springframework.data.domain.Sort.by(sortby).descending();
		}*/
		
		//org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize);
		//org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize, 
		//														org.springframework.data.domain.Sort.by(sortby));

		org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize, sort); 
		
		Page<Post> allPostPages = this.postRepo.findAll(pageable);
		
		List<Post> allPostContent = allPostPages.getContent();
		
		List<PostDto> allPostsDtos = allPostContent.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());		
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(allPostsDtos);
		postResponse.setPageNumber(allPostPages.getNumber());
		postResponse.setPageSize(allPostPages.getSize());
		postResponse.setTotalPage(allPostPages.getTotalPages());
		postResponse.setTotelPost(allPostPages.getTotalElements());
		postResponse.setLastpage(allPostPages.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByUser(Integer UserId){
		
		User user = this.userRepo.findById(UserId).orElseThrow(()-> new ResourceNotFoundException("User", "UserId",UserId));
		
		/*We will continue latter
		 * org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<Post> allPostPages = this.postRepo.findAll(pageable);
		
		List<Post> allPostContent = allPostPages.getContent();*/
		
		List<Post> allPosts = this.postRepo.findByUser(user);
		List<PostDto> allPostsDtos = allPosts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return allPostsDtos;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
		List<Post> allPosts = this.postRepo.findByCategory(category);
		List<PostDto> allPostsDtos = allPosts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return allPostsDtos;
	}

////	@Override
//	public PostResponse getAllPostByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
//		
//		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "UserId", userId));
//		
//		org.springframework.data.domain.Sort sort = null;
//		sort = sortDir.equalsIgnoreCase("asc")?org.springframework.data.domain.Sort.by(sortBy).ascending():org.springframework.data.domain.Sort.by(sortBy).descending();
//		
//		org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
//		
//		Page<Post> allPostPages = this.postRepo.findByUserId(user.getUserId(), pageable);
//		List<Post> allPostContent = allPostPages.getContent();
//		
//		List<PostDto> allPostDtos = allPostContent.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//		
//		PostResponse postResponse = new PostResponse();
//		postResponse.setContent(allPostDtos);
//		postResponse.setPageNumber(allPostPages.getNumber());
//		postResponse.setPageSize(allPostPages.getSize());
//		postResponse.setTotalPage(allPostPages.getTotalPages());
//		postResponse.setTotelPost(allPostPages.getTotalElements());
//		postResponse.setLastpage(allPostPages.isLast());
//		
//		return postResponse;
//	}
//
//	@Override
//	public PostResponse getAllPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
//	
//		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));
//		
//		org.springframework.data.domain.Sort sort = null;
//		sort = sortDir.equalsIgnoreCase("asc")?org.springframework.data.domain.Sort.by(sortBy).ascending():org.springframework.data.domain.Sort.by(sortBy).descending();
//		
//		org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
//		
//		Page<Post> allPostPages = this.postRepo.findByCategoryId(categoryId, pageable);
//		List<Post> allPostContent = allPostPages.getContent();
//		
//		List<PostDto> allPostDtos = allPostContent.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//		
//		PostResponse postResponse = new PostResponse();
//		postResponse.setContent(allPostDtos);
//		postResponse.setPageNumber(allPostPages.getNumber());
//		postResponse.setPageSize(allPostPages.getSize());
//		postResponse.setTotalPage(allPostPages.getTotalPages());
//		postResponse.setTotelPost(allPostPages.getTotalElements());
//		postResponse.setLastpage(allPostPages.isLast());
//		
//		return postResponse;
//	}
//
	@Override
	public List<PostDto> searchPost(String keyword) {
		
		List<Post> allPost = this.postRepo.findByPostTitleContaining(keyword);
		List<PostDto> allPostDtos = allPost.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return allPostDtos;
	}

}
