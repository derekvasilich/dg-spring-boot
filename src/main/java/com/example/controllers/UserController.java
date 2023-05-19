/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controllers;

import com.example.models.RefreshToken;
import com.example.models.User;
import com.example.repositories.UserRepository;
import com.example.request.LoginRequest;
import com.example.request.SignupRequest;
import com.example.request.TokenRefreshRequest;
import com.example.response.JwtResponse;
import com.example.response.MessageResponse;
import com.example.response.SignupResponse;
import com.example.response.TokenRefreshResponse;
import com.example.security.jwt.JwtUtils;
import com.example.security.service.RefreshTokenService;
import com.example.security.service.UserDetailsImpl;
import com.example.service.EmailServiceImpl;

import java.util.List;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author derek
 */
@RestController
@RequestMapping(path="/api")
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

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private EmailServiceImpl emailService;

	@PostMapping(path="/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
		);

		if (authentication == null) {
			return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(new MessageResponse("Access denied."));
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		String jwt = jwtUtils.generateJwtToken(userDetails);
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
			refreshToken.getToken(),
			userDetails.getEmail(), 
			userDetails.getId(),
			roles));
	}

	@PostMapping(path="/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest request) {
		// TODO check for existing account with email
		// thow error if found
		// add user to the DB
		if (userRepo.existsByEmail(request.getEmail())) {
			return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(new MessageResponse("A user with that email already exists!"));
		}
		User newUser = new User(
			request.getEmail(), 
			request.getFirstName(),
			request.getLastName(),
			passwordEncoder.encode(request.getPassword()));
		User savedUser = userRepo.save(newUser);

		emailService.sendSignupEmailForUser(savedUser);

		return ResponseEntity.ok(new SignupResponse("User registered successfully!", savedUser));
	}

	

	// @Value("${app.smtp.host}")
	// private String smtpHost;

	// @Value("${app.smtp.port}")
	// private int smtpPort;

	// @Value("${app.smtp.username}")
	// private String smtpUsername;

	// @Value("${app.smtp.password}")
	// private String smtpPassword;

	// @Bean
	// public JavaMailSender getJavaMailSender() {
	// 	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	// 	mailSender.setHost(smtpHost);
	// 	mailSender.setPort(smtpPort);
	// 	mailSender.setUsername(smtpUsername);
	// 	mailSender.setPassword(smtpPassword);

	// 	Properties props = mailSender.getJavaMailProperties();
	// 	props.put("mail.transport.protocol", "smtp");
	// 	props.put("mail.transport.auth", "true");
	// 	props.put("mail.transport.starttls.enable", "true");
	// 	props.put("mail.debug", "true");

	// 	return mailSender;
	// }

	@GetMapping(path="/users")
	@PreAuthorize("hasAuthority('developer') or hasAuthority('admin')")
	public @ResponseBody List<User> getUsers() {
		return userRepo.findAll();
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

