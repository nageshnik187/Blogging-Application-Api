package com.blog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.data.repository.query.Param;

import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.paylaods.PostDto;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	public List<Post> findByUser(User user);
	public List<Post> findByCategory(Category category);
	
//	public Page<Post> findByUserId(Integer userId, Pageable pageable);
//	public Page<Post> findByCategoryId(Integer categoryId, Pageable pageable);
	
	//Page<Post> findByCategoryIdContaining(Integer categoryId, Pageable pageable);
	
//	@Query("select p from Post p where p.title like :key")
//	public List<Post> findByTitleContaning(@Param("key") String title);
	
	List<Post> findByPostTitleContaining(String postTitle);
	
}
 