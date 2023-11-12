package com.blog.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.management.loading.PrivateClassLoader;
import javax.print.event.PrintJobAttributeEvent;

import org.apache.logging.log4j.util.StringBuilderFormattable;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "postID")
	private Integer postId;
	
	@Column(nullable = false)
	private String postTitle;
	
	@Column(nullable = false, length = 1000)
	private String postContent;
	
	private String postImage;
	
	private Date postDate;
	
	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "CategoryId")
	private Category category;
	
	@ManyToOne
	private Comment comment;
	
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Comment> comments = new HashSet<>();
	
}
