/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controllers;

import com.example.models.RefreshToken;
import com.example.repositories.UserRepository;
import com.example.request.LoginRequest;
import com.example.request.TokenRefreshRequest;
import com.example.response.JwtResponse;
import com.example.response.TokenRefreshResponse;
import com.example.security.jwt.JwtUtils;
import com.example.security.service.RefreshTokenService;
import com.example.security.service.UserDetailsImpl;
import java.util.List;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author derek
 */
@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
//	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepo;

	@Autowired
	RefreshTokenService refreshTokenService;
	
	@Autowired
	JwtUtils jwtUtils;	
		
	@PostMapping(path="/login")
	public @ResponseBody JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		String jwt = jwtUtils.generateJwtToken(userDetails);
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new JwtResponse(jwt, 
			refreshToken.getToken(),
			userDetails.getEmail(), 
			userDetails.getId(),
			roles);
	}

	@PostMapping(path="/refreshtoken")
	public @ResponseBody TokenRefreshResponse refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByToken(requestRefreshToken)
			.map(refreshTokenService::verifyExpiry)
			.map(RefreshToken::getUser)
			.map(user -> {
				String token = jwtUtils.generateJwtToken(user.email);
				return new TokenRefreshResponse(token, requestRefreshToken);
			})
			.orElseThrow(() -> new RuntimeException("Refresh token not found!"));
	}
	
}

