package com.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.paylaods.ApiResponse;

//This is the Global exception handler class
@RestControllerAdvice
public class GlobalExceptionHandler {

	//Resource Not Found exception Handler
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException ex){
		ApiResponse apiResponse = new ApiResponse(ex.getMessage(),false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	//This Will return the Message in API in case Validtion failed for the attributes
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> rsvp = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError)error).getField();
			String messageString =  error.getDefaultMessage();
			rsvp.put(fieldName, messageString);
		});
		
		return new ResponseEntity<Map<String,String>>(rsvp, HttpStatus.BAD_REQUEST); 
	}
	
	
	//Resource Not Found exception Handler
		@ExceptionHandler(ApiException.class)
		public ResponseEntity<ApiResponse> handleApiException(ApiException ex){
			ApiResponse apiResponse = new ApiResponse(ex.getMessage(),true);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	
}
