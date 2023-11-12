package com.blog.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exceptions.ApiException;
import com.blog.paylaods.JwtAuthRequest;
import com.blog.paylaods.JwtAuthResponse;
import com.blog.paylaods.UserDto;
import com.blog.security.JwtTokenHelper;
import com.blog.service.UserService;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	private void authenticate(String userName, String password) {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Username Or Password !!!");
			throw new ApiException("Invalid Username Or Password !!!");
			
		}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request){
	
		this.authenticate(request.getUserName(), request.getUserPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserName());
		
		String generatedToken = this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse response = new JwtAuthResponse();
		
		response.setToken(generatedToken);
		
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
		
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		
		UserDto registedUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registedUser,HttpStatus.CREATED);
	}
	
}
	
	