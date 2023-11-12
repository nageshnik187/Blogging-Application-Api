package com.blog.paylaods;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PostResponse {

	private List<PostDto> content;
	private Integer pageNumber;
	private Integer pageSize;
	private Integer totalPage;
	private long totelPost;
	private boolean lastpage;
	
}
