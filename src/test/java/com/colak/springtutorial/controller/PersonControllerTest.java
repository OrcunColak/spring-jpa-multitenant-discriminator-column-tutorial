package com.colak.springtutorial.controller;

import com.colak.springtutorial.filter.TenantContext;
import com.colak.springtutorial.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository repository;

    @BeforeEach
    public void setup() {
        TenantContext.clear();
    }

    @Test
    public void testGetPersonsForTenant1() throws Exception {
        // Set tenant to 'tenant_1'
        TenantContext.setCurrentTenant("tenant_1");

        mockMvc.perform(MockMvcRequestBuilders.get("/persons"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Jane Smith"));
    }

    @Test
    public void testGetPersonsForTenant2() throws Exception {
        // Set tenant to 'tenant_2'
        TenantContext.setCurrentTenant("tenant_2");

        mockMvc.perform(MockMvcRequestBuilders.get("/persons"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Bob Brown"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Alice Green"));
    }

}