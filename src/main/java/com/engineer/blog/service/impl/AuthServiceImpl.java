package com.engineer.blog.service.impl;


import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.engineer.blog.entity.Role;
import com.engineer.blog.entity.User;
import com.engineer.blog.exception.BlogAPIException;
import com.engineer.blog.payload.LoginDto;
import com.engineer.blog.payload.SignUpDto;
import com.engineer.blog.repository.RoleRepository;
import com.engineer.blog.repository.UserRepository;
import com.engineer.blog.security.JwtTokenProvider;
import com.engineer.blog.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	

	public AuthServiceImpl(AuthenticationManager authenticationManager, 
			UserRepository userRepository,
			RoleRepository roleRepository, 
			PasswordEncoder passwordEncoder,
			JwtTokenProvider jwtTokenProvider) {
		
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider= jwtTokenProvider;
		
	}

	@Override
	public String login(LoginDto loginDto) {
      Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
    		  loginDto.getUsernameOrEmail(),
    		  loginDto.getPassword() )); 
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String token=jwtTokenProvider.generateToken(authentication);
      return token;
		
      
	}

	@Override
	public String signUp(SignUpDto signUpDto) {
		//check user is exist or not in database
		if(userRepository.existsByUsername(signUpDto.getUsername())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Username is already exists!.");
		}
		//check for email check in db
		if(userRepository.existsByEmail(signUpDto.getEmail())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Email is already exists!.");
		}
		
		User user=new User();
		user.setName(signUpDto.getName());
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		Set<Role>roles=new HashSet<>();
		Role userRole=roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);
		return "User Registered successfully";
	}

}
