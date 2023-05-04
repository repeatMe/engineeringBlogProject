package com.engineer.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
private String resourceName;
private String fieldName;
private long filedValue;
public ResourceNotFoundException(String resourceName, String fieldName,long fieldValue) {
	super(String.format("%snot found with %s:%S",resourceName,fieldName,fieldValue));
	this.resourceName = resourceName;
	this.fieldName = fieldName;
	this.filedValue= fieldValue;
		
     }
public String getResourceName() {
	return resourceName;
}

public String getFieldName() {
	return fieldName;
}

public long getFiledValue() {
	return filedValue;
}


}
