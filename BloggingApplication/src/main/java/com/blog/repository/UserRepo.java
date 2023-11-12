package com.blog.repository;

import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	java.util.Optional<User> findByUserEmail(String userEmail);

}
