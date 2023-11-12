package com.blog.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.blog.paylaods.CategoryDto;
import com.blog.repository.CategoryRepo;

//We are not using any access specifier as In interface all methodes are by default public

public interface CategoryService {
	
	
	//Create Category
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//Update Category
	CategoryDto updateCategoryById(CategoryDto categoryDto, Integer categoryId);
	
	//Delete Category
	void deleteCategoryById(Integer categoryId);
	
	//Get Category
	CategoryDto getCategoryById(Integer categoryId);
	
	//Get All Categories
	List<CategoryDto> getAllCategory();
	
	
}
