package com.engineer.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineer.blog.payload.LoginDto;
import com.engineer.blog.payload.SignUpDto;
import com.engineer.blog.security.JWTAuthResponse;
import com.engineer.blog.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
private AuthService authService;

public AuthController(AuthService authService) {
	this.authService=authService;
}
//Build  login REST API

@PostMapping(value={"/signIn","/login"}) //client can use anyone
public ResponseEntity<JWTAuthResponse>login(@RequestBody LoginDto loginDto){
	String token=authService.login(loginDto);
	
	JWTAuthResponse jwtAuthResponse =new  JWTAuthResponse();
	jwtAuthResponse.setAccessToken(token);
	return ResponseEntity.ok(jwtAuthResponse);
}
//Build signup/registration API
@PostMapping(value={"/register","/signUp"}) //client can use anyone
public ResponseEntity<String>signUp(@RequestBody SignUpDto signUpDto){
	String response=authService.signUp(signUpDto);
	return new ResponseEntity<>(response,HttpStatus.CREATED);
	
	
}
}
