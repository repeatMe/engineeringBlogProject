package com.engineer.blog.service;

import com.engineer.blog.payload.LoginDto;
import com.engineer.blog.payload.SignUpDto;

public interface AuthService {
String login(LoginDto loginDto);
String signUp(SignUpDto signUpDto);
}
