package com.blog.paylaods;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Empty;

import com.blog.entity.Role;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int userId;
	
	@NotEmpty
	@Size(min = 4, message = "Name is minimum should be 4 characters !!!")
	private String userName;
	
	@Email(message = "EmailAddress Not Valid !!!")
	private String userEmail;
	
	@NotEmpty
	@Size(min = 3, max = 10, message = "Password should be minimum 3 and maximum should be 10 character !!!")
	//@Pattern(regular expression) using this annotation we can restrict password pattern
	private String userPassword;
	
	@NotEmpty
	private String userAbout;
	
	
	private Set<RoleDto> roles = new HashSet<>();
}
