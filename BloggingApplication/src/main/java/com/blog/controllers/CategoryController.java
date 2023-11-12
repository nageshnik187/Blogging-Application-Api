package com.blog.controllers;

import java.util.List;

import org.hibernate.collection.spi.PersistentIdentifierBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.blog.paylaods.ApiResponse;
import com.blog.paylaods.CategoryDto;
import com.blog.service.CategoryService;

import jakarta.persistence.Id;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	//Add Category Handler
	@PostMapping("/")
	public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto){
		
		CategoryDto createCategoryDto = this.categoryService.createCategory(categoryDto);		
		return new ResponseEntity<CategoryDto>(createCategoryDto,HttpStatus.CREATED);
	}
	
	//Update Category Handler
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		
		CategoryDto updateCategoryDto = this.categoryService.updateCategoryById(categoryDto, categoryId);
		
		return new ResponseEntity<CategoryDto>(updateCategoryDto,HttpStatus.OK);
	}
	
	//Delete Category Handler
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
		
		this.categoryService.deleteCategoryById(categoryId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully...", true),HttpStatus.OK);
	}
	
	//Get Category By Id
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
		
		CategoryDto fetcheCategoryDto = this.categoryService.getCategoryById(categoryId);
		
		return ResponseEntity.ok(fetcheCategoryDto);
	}
	
	//Get All Category
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		
		List<CategoryDto> allCategoryDtos = this.categoryService.getAllCategory();		
		return ResponseEntity.ok(allCategoryDtos);
	}
	
}
