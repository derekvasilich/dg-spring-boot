/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dg.controllers;

import java.util.HashMap;
import java.util.List;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.kafka.core.KafkaTemplate;
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

// import com.dg.configuration.KafkaTopicConfig;
import com.dg.models.RefreshToken;
import com.dg.models.User;
import com.dg.repositories.UserRepository;
import com.dg.request.LoginRequest;
import com.dg.request.SignupRequest;
import com.dg.request.TokenRefreshRequest;
import com.dg.response.JwtResponse;
import com.dg.response.MessageResponse;
import com.dg.response.SignupResponse;
import com.dg.response.TokenRefreshResponse;
import com.dg.security.jwt.JwtUtils;
import com.dg.security.service.RefreshTokenService;
import com.dg.security.service.UserDetailsImpl;
import com.dg.service.EmailServiceImpl;

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
	EmailServiceImpl emailService;

	// @Autowired
	// private KafkaTemplate<String, String> kafkaTemplate;

    // inject via application.properties
    @Value("${pod-name:}")
    private String podName;
    // inject via application.properties
    @Value("${pod-namespace:}")
    private String podNamespace;
    // inject via application.properties
    @Value("${pod-id:}")
    private String podId;

	@GetMapping(path="/status")
	public ResponseEntity<?> getStatus() {
		HashMap<String, String> map = new HashMap<>();	
		if (!podName.isEmpty()) 		map.put("podName", podName);
		if (!podNamespace.isEmpty()) 	map.put("podNamespace", podNamespace);
		if (!podId.isEmpty()) 			map.put("podId", podId);
		try {
			map.put("userCount", Long.toString(userRepo.count()));
		} catch (Exception e) {
			map.put("error", e.getMessage());
		}
		return ResponseEntity.ok(map);
	}

	@PostMapping(path="/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
		);

		if (authentication == null) {
			return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(new MessageResponse<String>("Access denied."));
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
		if (!request.isValid()) {
			return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new MessageResponse<List<String>>(request.getMessages()));
		}
		if (userRepo.existsByEmail(request.getEmail())) {
			return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new MessageResponse<String>("That Email already exists!"));
		}
		User newUser = new User(
			request.getEmail(), 
			request.getFirstName(),
			request.getLastName(),
			passwordEncoder.encode(request.getPassword())
		);
		User savedUser = userRepo.save(newUser);

//		kafkaTemplate.send(KafkaTopicConfig.TOPIC_SIGNUP, newUser.email);
		emailService.sendSignupEmailForUser(newUser.email);

		return ResponseEntity.ok(new SignupResponse("User registered successfully!", savedUser));
	}

	@GetMapping(path="/users")
	@PreAuthorize("hasAuthority('developer') or hasAuthority('admin')")
	public @ResponseBody List<User> getUsers() {
		return userRepo.findAll();
	}

	@PostMapping(path="/refreshtoken")
	public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();
		try {
			return refreshTokenService.findByToken(requestRefreshToken)
				.map(refreshTokenService::verifyExpiry)
				.map(RefreshToken::getUser)
				.map(user -> {
					String token = jwtUtils.generateJwtToken(user.email);
					return ResponseEntity
						.status(HttpStatus.OK)
						.body(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow();
		} catch (RuntimeException e) {
			return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(new MessageResponse<String>(e.getMessage()));
		}
	}
	
}

