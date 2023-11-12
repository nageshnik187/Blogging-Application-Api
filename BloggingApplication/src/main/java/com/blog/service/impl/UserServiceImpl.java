package com.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstant;
import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.paylaods.UserDto;
import com.blog.repository.RoleRepo;
import com.blog.repository.UserRepo;
import com.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	//Generally Model Mapper Liberary is used to convert the one model object to another one
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		
		//UserDto userDto2 = this.userToDto(savedUser);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUserById(UserDto userDto, Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		user.setUserName(userDto.getUserName());
		user.setUserEmail(userDto.getUserEmail());
		user.setUserPassword(userDto.getUserPassword());
		user.setUserAbout(userDto.getUserAbout());
		
		//UserDto userDto2 = this.userRepo.getById(userId);
		User updatedUser = this.userRepo.save(user);
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		return this.userToDto(user);
	}

	@Override
	public void deleteUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		this.userRepo.delete(user);
		
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> allUers= this.userRepo.findAll();
		
		/*We can do like this
		 * List<UserDto> allDtoUsers = null;
		for(User user : allUers) {
			allDtoUsers.add(this.userToDto(user));
		} I*/
		
		//OR Like Below Using Stream AP
		List<UserDto> allDtoUsers = allUers.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
		
		return allDtoUsers;
	}
	
	//DtoUser to User 
	public User dtoToUser(UserDto userDto) {
		
		/*User user = new User();
		
		user.setUserId(userDto.getUserId());
		user.setUserName(userDto.getUserName());
		user.setUserEmail(userDto.getUserEmail());
		user.setUserPassword(userDto.getUserPassword());
		user.setUserAbout(userDto.getUserAbout());
		At PLease of above conversion we can use model mapper class*/
		User user = this.modelMapper.map(userDto,User.class);
		return user;
	}
	
	//User to DtoUser
	public  UserDto userToDto(User user) {
		
		/*UserDto userDto = new UserDto();
		
		userDto.setUserId(user.getUserId());
		userDto.setUserName(user.getUserName());
		userDto.setUserEmail(user.getUserEmail());
		userDto.setUserPassword(user.getUserPassword());
		userDto.setUserAbout(user.getUserAbout());
		Using Model Mapper Liberary*/
		
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {	
		
		User user = this.modelMapper.map(userDto, User.class);
		
		//Encode the password
		user.setUserPassword(this.passwordEncoder.encode(user.getUserPassword()));
		
		//Add Normal role with the user	
		Role role = this.roleRepo.findById(AppConstant.ROLE_NORMAL).get();
		
		user.getRoles().add(role);

		System.out.println("Insider register func after sav "+user.getUserEmail() + " And Name "+ user.getUsername());
		User newUser = this.userRepo.save(user);
		
		System.out.println("Insider register func after saving in DB "+newUser.getUserEmail() + " And Name "+ newUser.getUsername());
		
		return this.modelMapper.map(newUser, UserDto.class);
	}

}
