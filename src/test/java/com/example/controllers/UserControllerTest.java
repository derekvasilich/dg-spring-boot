package com.example.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.NotImplementedException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.example.configuration.WebSecurityTestConfig;
import com.example.models.RefreshToken;
import com.example.models.User;
import com.example.repositories.*;
import com.example.request.LoginRequest;
import com.example.request.SignupRequest;
import com.example.request.TokenRefreshRequest;
import com.example.security.service.RefreshTokenService;
import com.example.service.EmailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = WebSecurityTestConfig.class
)
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
	private UserRepository userRepo;

    @MockBean
    private EmailServiceImpl emailServiceImpl;

    @MockBean
    private RefreshTokenService refreshTokenService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @WithUserDetails("user@company.com")
    void shouldAuthenticateValidLoginRequest() throws Exception {
        String pwd = "password";
        User user = new User(
            "user@company.com", 
            "test", 
            "user", 
            passwordEncoder.encode(pwd));
        LoginRequest login = new LoginRequest("user@company.com", pwd);
        RefreshToken refreshToken = new RefreshToken(
            user,
            Instant.now().plusMillis(86400000),
            UUID.randomUUID().toString()
        );

        when(userRepo.findByEmail("user@company.com"))
            .thenReturn(Optional.of(user));

        when(refreshTokenService.createRefreshToken(Mockito.anyLong()))
            .thenReturn(refreshToken);

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("token").exists())
            .andExpect(jsonPath("type").value("Bearer"))
            .andExpect(jsonPath("refreshToken").value(refreshToken.getToken()))
            .andExpect(jsonPath("username").value(user.email))
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("roles").isArray())
            .andDo(print());
    }

    @Test
    @WithUserDetails("user@company.com")
    void shouldNotAuthenticateInvalidLoginRequest() throws Exception {
        String pwd = "wrongPassword";
        LoginRequest login = new LoginRequest("user@company.com", pwd);

        when(userRepo.findByEmail("user@company.com"))
            .thenReturn(null);

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("dev@company.com")
    void shouldListUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void shouldRefreshToken() throws Exception {
        String token = UUID.randomUUID().toString();
        User user = new User(
            "user@company.com", 
            "test", 
            "user", 
            passwordEncoder.encode("password"));
        RefreshToken refreshToken = new RefreshToken(
            user,
            Instant.now().plusMillis(86400000),
            token
        );
        TokenRefreshRequest req = new TokenRefreshRequest();
        req.setRefreshToken(token);

        when(refreshTokenService.findByToken(token))
            .thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.verifyExpiry(Mockito.any(RefreshToken.class)))
            .thenReturn(refreshToken);

        mockMvc.perform(post("/api/refreshtoken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("accessToken").exists())
            .andExpect(jsonPath("refreshToken").value(token))
            .andExpect(jsonPath("tokenType").value("Bearer"))
            .andDo(print());            
    }

    @Test
    void shouldNotRefreshExpiredToken() throws Exception {
        String token = UUID.randomUUID().toString();
        User user = new User(
            "user@company.com", 
            "test", 
            "user", 
            passwordEncoder.encode("password"));
        RefreshToken refreshToken = new RefreshToken(
            user,
            Instant.now().minusMillis(86400000),
            token
        );
        TokenRefreshRequest req = new TokenRefreshRequest();
        req.setRefreshToken(token);

        when(refreshTokenService.findByToken(token))
            .thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.verifyExpiry(Mockito.any(RefreshToken.class)))
            .thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/refreshtoken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("message").hasJsonPath())
            .andDo(print());
    }

    @Test
    void shouldNotRegisterExistingUser() throws Exception {
        String email = "test@example.com";

        SignupRequest signup = new SignupRequest(
            email, 
            "tester", 
            "test", 
            "abc123");
        
        when(userRepo.existsByEmail(email))
            .thenReturn(true);

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signup)))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(print());        
    }

    @Test
    void shouldRegisterNewUser() throws Exception {
        String email = "test@example.com";

        SignupRequest signup = new SignupRequest(
            email, 
            "tester", 
            "test", 
            "abc123");
        
        User user = new User(
            signup.getEmail(), 
            signup.getFirstName(),
            signup.getLastName(), 
            passwordEncoder.encode(signup.getPassword()));
        
        when(userRepo.save(Mockito.any(User.class)))
            .thenReturn(user);
        mock(EmailServiceImpl.class, "sendSignupEmailForUser");

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signup)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("message").value("User registered successfully!"))
            .andExpect(jsonPath("user").exists())
            .andExpect(jsonPath("user.id").hasJsonPath())
            .andExpect(jsonPath("user.firstName").value(signup.getFirstName()))
            .andExpect(jsonPath("user.lastName").value(signup.getLastName()))
            .andExpect(jsonPath("user.email").value(email))
            .andExpect(jsonPath("user.role").value("user"))
            .andDo(print());
    }    
}
