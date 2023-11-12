package com.blog.paylaods;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.entity.Category;
import com.blog.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto {
	
	private Integer postId;
	
	@NotEmpty
	@Size(max = 20, message = "Enter the Title of the post of size under 20 character")
	private String postTitle;
	
	@NotEmpty
	@Size(min = 10, max = 1000, message = "Enter the postContent under size")
	private String postContent;	
	
	private Date postDate;
	
	private String postImage;
	
	private UserDto user;
	
	private CategoryDto category;  

	private Set<CommentDto> comments = new HashSet<>();
	
}
