package com.blog.paylaods;

import org.hibernate.type.descriptor.java.IntegerJavaType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private int categoryId;
	
	@NotEmpty
	@Size(min = 10, message = "Title is minimum should be 10 characters !!!")
	private String categoryTitle;
	
	@NotEmpty
	@Size(min = 15, message = "Description is minimum should be 15 characters !!!")
	private String categoryDescription;
	
}
