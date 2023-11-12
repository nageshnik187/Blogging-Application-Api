package com.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entity.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Load the user useing the username from DB
		User user = this.userRepo.findByUserEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "UserEmail"+username, 0));
		
		
		return user;
	}

}
