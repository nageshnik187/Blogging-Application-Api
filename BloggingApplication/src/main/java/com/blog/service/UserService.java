package com.blog.service;

import java.util.List;

import com.blog.entity.User;
import com.blog.paylaods.UserDto;

public interface UserService {
	
	//Register new user
	UserDto registerNewUser(UserDto userDto);
	
	//create user
	UserDto createUser(UserDto userDto);
	
	//update user
	UserDto updateUserById(UserDto userDto, Integer userId);
	
	//get user
	UserDto getUserById(Integer userId);
	
	//delete user 
	void deleteUserById(Integer userId);
	
	//get all user
	List<UserDto> getAllUsers();
	
  
}
