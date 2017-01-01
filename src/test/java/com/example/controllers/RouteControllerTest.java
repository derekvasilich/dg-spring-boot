package com.example.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.example.configuration.WebSecurityTestConfig;
import com.example.models.Route;
import com.example.models.Quote;
import com.example.models.User;
import com.example.models.RouteLocationVisit;
import com.example.repositories.QuoteRepository;
import com.example.repositories.RouteLocationVisitRepository;
import com.example.repositories.UserRepository;
import com.example.repositories.RouteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = WebSecurityTestConfig.class
)
@AutoConfigureMockMvc
@WithUserDetails("admin@company.com")
public class RouteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
	private RouteRepository routeRepo;

    @MockBean
	private RouteLocationVisitRepository visitRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnRoutes() throws Exception {
        mockMvc.perform(get("/api/routes")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void shouldReturnRouteItem() throws Exception {
        Route route = new Route();
        route.id = 1L;
        route.startAddress = 0;
        route.truck = "A";

        when(routeRepo.findById(1L))
            .thenReturn(Optional.of(route));

        mockMvc.perform(get("/api/routes/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void should404OnMissingRoute() throws Exception {
        mockMvc.perform(get("/api/routes/90210")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }

    @Test
    void shouldSaveRouteVisit() throws Exception {
        RouteLocationVisit visit = new RouteLocationVisit(1L, 1L, 1L);

        when(visitRepo.save(Mockito.any(RouteLocationVisit.class)))
            .thenReturn(visit);

        mockMvc.perform(post("/api/routes/visit")
                .content(objectMapper.writeValueAsString(visit))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").hasJsonPath())
            .andExpect(jsonPath("routeId").value(1L))
            .andExpect(jsonPath("quoteId").value(1L))
            .andExpect(jsonPath("userId").value(1L))
            .andExpect(jsonPath("visitedAt").exists())
            .andDo(print());
    }

    @Test
    void shouldReturnRoutesPaginated() throws Exception {
        mockMvc.perform(get("/api/routes/paginated")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }


}
