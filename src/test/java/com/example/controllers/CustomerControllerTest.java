package com.example.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.example.configuration.WebSecurityTestConfig;
import com.example.models.Customer;
import com.example.repositories.CustomerRepository;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = WebSecurityTestConfig.class
)
@AutoConfigureMockMvc
@WithUserDetails("admin@company.com")
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
	private CustomerRepository custRepo;

    @Test
    void shouldReturnCustomer() throws Exception {
        Customer cust = new Customer();
        cust.firstName = "test";
        cust.lastName = "tester";
        cust.id = 1L;
        cust.email = "test@company.com";

        when(custRepo.findById(1L))
            .thenReturn(Optional.of(cust));

        mockMvc.perform(get("/api/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void should404OnMissingCustomer() throws Exception {
        mockMvc.perform(get("/api/customers/90210")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }


    @Test
    void shouldShouldListCustomers() throws Exception {
        mockMvc.perform(get("/api/customers")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }    
}
