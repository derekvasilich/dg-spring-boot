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
import com.example.models.Vehicle;
import com.example.repositories.VehicleRepository;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = WebSecurityTestConfig.class
)
@AutoConfigureMockMvc
@WithUserDetails("admin@company.com")
public class VehicleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleRepository vehicleRepo;

    @Test
    void shouldReturnVehicles() throws Exception {
        mockMvc.perform(get("/api/vehicles")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void shouldGetVehicle() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.id = 1L;
        vehicle.name = "Toyota Tacoma";

        when(vehicleRepo.findById(1L))
            .thenReturn(Optional.of(vehicle));

        mockMvc.perform(get("/api/vehicles/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void should404OnMissingVehicle() throws Exception {
        mockMvc.perform(get("/api/vehicles/90210")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }

    @Test
    void shouldReturnVehiclesPaginated() throws Exception {
        mockMvc.perform(get("/api/vehicles/paginated")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }


}
