package com.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.blog.entity.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.paylaods.CategoryDto;
import com.blog.repository.CategoryRepo;
import com.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepo.save(category);
		
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategoryById(CategoryDto categoryDto, Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		this.categoryRepo.save(category);
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public void deleteCategoryById(Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId",categoryId));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryID", categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		
		List<Category> allCategories = this.categoryRepo.findAll();
		List<CategoryDto> allCategoryDtos = allCategories.stream().map(categoty-> this.modelMapper.map(categoty, CategoryDto.class)).collect(Collectors.toList());
		
		return allCategoryDtos;
	}

}
