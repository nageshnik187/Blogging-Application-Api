package com.blog.controllers;

import java.util.List;
import java.util.Map;

import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.blog.paylaods.ApiResponse;
import com.blog.paylaods.UserDto;
import com.blog.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	//Create User handler
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createdUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
	}

	//Update USer handler
	//Here @valid annotation is used to validate the UserDto attribute values
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid){
		
		//UserDto updatedUser = this.userService.updateUserById(userDto, uid);
		//return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK); Or Like below
		return ResponseEntity.ok(this.userService.updateUserById(userDto, uid));
	}
	
	//Adding role based security this method can be called by admin only
	@PreAuthorize("hasRole('ADMIN')")
	//Delete User
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
		
		this.userService.deleteUserById(userId);
		return new ResponseEntity(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
	}	
	
	//Get All Users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser(){
		//return new ResponseEntity<List<UserDto>>(this.userService.getAllUsers(), HttpStatus.ACCEPTED); Or Like below 
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	//Get User By Id
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable Integer userId){
	
		UserDto fetchedUserDto = this.userService.getUserById(userId);
		return ResponseEntity.ok(fetchedUserDto);
	}
}
