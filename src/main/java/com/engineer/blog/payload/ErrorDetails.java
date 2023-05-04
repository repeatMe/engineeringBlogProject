package com.engineer.blog.payload;

import java.util.Date;

public class ErrorDetails {
 private Date timeStammp;
 private String message;
 private String details;
public ErrorDetails(Date timeStammp, String message, String details) {
	
	this.timeStammp = timeStammp;
	this.message = message;
	this.details = details;
}
public Date getTimeStammp() {
	return timeStammp;
}

public String getMessage() {
	return message;
}

public String getDetails() {
	return details;
}

 
 
}
