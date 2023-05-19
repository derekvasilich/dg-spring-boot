package com.example.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.example.configuration.WebSecurityTestConfig;
import com.example.models.User;
import com.example.repositories.*;
import com.example.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = WebSecurityTestConfig.class
)
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
	private AuthenticationManager authenticationManager;
	
    @MockBean
	private UserRepository userRepo;

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
        User user = new User("user@company.com", "test", "user", passwordEncoder.encode(pwd));
        LoginRequest login = new LoginRequest("user@company.com", pwd);
        when(userRepo.findByEmail("user@company.com")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @WithUserDetails("user@company.com")
    void shouldNotAuthenticateInvalidLoginRequest() throws Exception {
        String pwd = "wrongPassword";
        LoginRequest login = new LoginRequest("test@example.com", pwd);

        when(userRepo.findByEmail("test@example.com")).thenReturn(null);
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isForbidden());
    }

    @Test
    void shouldRefreshToken() throws Exception {
        throw new NotImplementedException("Not implemented");
    }

    @Test
    void shouldNotRefreshExpiredToken() throws Exception {
        throw new NotImplementedException("Not implemented");
    }

    @Test
    void shouldRegisterNewUser() throws Exception {
        throw new NotImplementedException("Not implemented");
    }
}
