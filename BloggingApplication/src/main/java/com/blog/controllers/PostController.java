package com.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstant;
import com.blog.paylaods.ApiResponse;
import com.blog.paylaods.PostDto;
import com.blog.paylaods.PostResponse;
import com.blog.paylaods.UserDto;
import com.blog.service.FileService;
import com.blog.service.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	//Find path
	@Value("${project.image}")
	private String path;
	
	//Create POst Handler
	@PostMapping("/users/{userId}/categories/{categoryId}/posts")
	public ResponseEntity<PostDto> createPostHandler(@RequestBody PostDto postDto,
			@PathVariable Integer userId, 
			@PathVariable Integer categoryId){
		
		PostDto createdPost = this.postService.createPost(postDto,userId,categoryId);
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
	}
	
	//Update Post Handler
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePostHandler(@RequestBody PostDto postDto, 
			@PathVariable Integer postId){

		PostDto updatedPostDto = this.postService.updatePostById(postDto, postId);
		return ResponseEntity.ok(updatedPostDto);
	}
	
	//Delete POst Handler
	@DeleteMapping("/posts/{postId}")  //{postId} this is called as path URI variable
	public ResponseEntity<ApiResponse> deletePostHandler(@PathVariable Integer postId){
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully...", true),HttpStatus.OK);
	}
	
	
	//Get Post By PostId Handler
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostHandler(@PathVariable Integer postId){
		PostDto fetchedPostDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(fetchedPostDto, HttpStatus.OK);
	}
	
	//Get All Post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPostHandler(
			@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIRECTION, required = false) String sortDir
			){
		PostResponse allPost = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
	}
	
//	//Get All POst related to user by userid
//	@GetMapping("/users/{userId}")
//	public ResponseEntity<PostResponse> getAllPostByUserHandler(
//			@PathVariable Integer userId,
//			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
//			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
//			@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
//			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
//			){
//		PostResponse allPost = this.postService.getAllPostByUser(userId,pageNumber,pageSize,sortBy,sortDir);
//		return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
//	}
	
	//Get All Post By User
	@GetMapping("/users/{userId}/posts")
	public ResponseEntity<List<PostDto>> getAllPostByUserHandler(@PathVariable Integer userId){
		List<PostDto> allPost= this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(allPost,HttpStatus.OK);
	}
	
	// Get All Post By Category
	@GetMapping("/categories/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getAllPostByCategoryHandler(@PathVariable Integer categoryId) {
		List<PostDto> allPost = this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(allPost, HttpStatus.OK);
	}

	//Search all post By title Keyword
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostHandler(@PathVariable String keyword){
		
		List<PostDto> searchPostDtos = this.postService.searchPost(keyword);
		return ResponseEntity.ok(searchPostDtos);
	}
	
	
	//Upload image to particular postid
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam ("image") MultipartFile image, @PathVariable Integer postId) throws IOException{
		
		String uploadedImage = this.fileService.uploadImage(path, image);
		PostDto postDto = this.postService.getPostById(postId);
		postDto.setPostImage(uploadedImage);
		PostDto updatedPostDto = this.postService.updatePostById(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);
	} 
	
	//Download Image by image name
	@GetMapping(value = "/post/image/download/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
}
