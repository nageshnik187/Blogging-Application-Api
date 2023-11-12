package com.blog.paylaods;

import lombok.Data;

//This annotation is used at place of getter and setter anotatiomn
@Data
public class JwtAuthResponse {

	private String token;
	
}
