package com.engineer.blog.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.engineer.blog.payload.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler {
//handle specificexception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails>handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest){
		ErrorDetails errorDetails=new ErrorDetails(new  Date(),exception.getMessage(),
				webRequest.getDescription(false));
		
				return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(BlogAPIException.class)
	public ResponseEntity<ErrorDetails>handleBlogAPIException(BlogAPIException exception,
			WebRequest webRequest){
		ErrorDetails errorDetails=new ErrorDetails(new  Date(),exception.getMessage(),
				webRequest.getDescription(false));
		
				return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails>handleGlobalException(Exception exception,
			WebRequest webRequest){
		ErrorDetails errorDetails=new ErrorDetails(new  Date(),exception.getMessage(),
				webRequest.getDescription(false));
		
				return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
		
	}
	
}
