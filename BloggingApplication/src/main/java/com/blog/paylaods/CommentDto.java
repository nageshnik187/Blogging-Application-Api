package com.blog.paylaods;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDto {
	
	private Integer commentId;
	
	private String commentContent;

}
