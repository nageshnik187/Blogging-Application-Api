package com.blog.security;

import java.io.IOException;

import javax.management.loading.PrivateClassLoader;
import javax.sound.midi.Soundbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//4. JwtAuthenticationFilter  extends OnceRequestFilter                   
//Get jwt token from request
//Validate token
//Get user from token
//Load user associated with token
//Set spring security 

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper JwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//Get jwt token from request  "Authorization" is the key we pas from form or in the form of XML 
		// Token exmple: Bearer 47867583hjkhh5435
		String requestToken = request.getHeader("Authorization");
		
		System.out.println("Request Token = "+ requestToken);
		
		String userName = null;
		
		String token = null;
		
		if(requestToken != null && requestToken.startsWith("Bearer")) {
			
			token = requestToken.substring(7);
			try {
				userName = this.JwtTokenHelper.getUserNameFromToken(token);
			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
				System.out.println("Unable to get JWT token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT token has expired");
			} catch (MalformedJwtException e) {
				System.out.println("Invalid Jwt");
			}
			
		}else {
			System.out.println("JWT token does not begin with Bearer");
		}
		
		//Once we get the token now Validate token
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			//load user by username
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			
			if(this.JwtTokenHelper.validateToken(token, userDetails)) {
				
				//TO set authentication need UsernamePasswordAuthenticationToken refrence same we are fetching
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				//Everything working fine now set authentication
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}else {
				System.out.println("Invalid JWT Token");
			}
			
		}else {
			System.out.println("Username is null or security context is not null");
		}
		
		filterChain.doFilter(request, response);
	}

}
