package com.blog.paylaods;

import lombok.Data;

@Data
public class JwtAuthRequest {

	private String userName;
	
	private String userPassword;
}
