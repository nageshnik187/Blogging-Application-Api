package com.blog.exceptions;

import com.blog.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	private String resourceName;
	private String fieldName;
	private long fildValue;
	
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue){
		super(String.format("%s Not Found For %s = %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fildValue = fieldValue;
	}
	
}
