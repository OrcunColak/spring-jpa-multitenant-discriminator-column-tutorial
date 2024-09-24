package com.colak.springtutorial.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testGetPersonsForTenant1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persons")
                        .header("X-PrivateTenant", "tenant_1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Jane Smith"));
    }

    @Test
    public void testGetPersonsForTenant2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/persons")
                        .header("X-PrivateTenant", "tenant_2"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Bob Brown"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Alice Green"));
    }

    @Test
    public void testCreatePersonForTenant1() throws Exception {
        String newPersonJson = """
                {
                    "id": 5,
                    "name": "New Tenant 1 User",
                    "age": 29,
                    "email": "newuser@example.com"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/persons")
                        .header("X-PrivateTenant", "tenant_1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPersonJson))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Tenant 1 User"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("newuser@example.com"));

        // Ensure the new person is stored under tenant_1
        mockMvc.perform(MockMvcRequestBuilders.get("/persons")
                        .header("X-PrivateTenant", "tenant_1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3)) // 2 existing + 1 new
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("New Tenant 1 User"));
    }

}