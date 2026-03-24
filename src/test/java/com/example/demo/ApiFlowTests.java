package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class ApiFlowTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deleteEventWithRegistrationsShouldSucceed() throws Exception {
        Map<String, Object> createEvent = new HashMap<>();
        createEvent.put("name", "Exam Event");
        createEvent.put("date", "2026-04-20");
        createEvent.put("location", "Chennai");

        MvcResult createEventResult = mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createEvent)))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode createdEventJson = objectMapper.readTree(createEventResult.getResponse().getContentAsString());
        long eventId = createdEventJson.get("id").asLong();

        Map<String, Object> registration = new HashMap<>();
        registration.put("eventId", eventId);
        registration.put("name", "Student");
        registration.put("email", "student@example.com");
        registration.put("phone", "9999999999");

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registration)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.event.id").value(eventId));

        mockMvc.perform(delete("/api/events/{id}", eventId))
                .andExpect(status().isOk());
    }

    @Test
    void invalidEventOperationsShouldReturnNotFound() throws Exception {
        long missingId = 999999L;

        Map<String, Object> updateEvent = new HashMap<>();
        updateEvent.put("name", "Missing");
        updateEvent.put("date", "2026-05-01");
        updateEvent.put("location", "Nowhere");

        mockMvc.perform(put("/api/events/{id}", missingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateEvent)))
                .andExpect(status().isNotFound());

        Map<String, Object> registration = new HashMap<>();
        registration.put("eventId", missingId);
        registration.put("name", "Student");
        registration.put("email", "student@example.com");
        registration.put("phone", "9999999999");

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registration)))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/api/events/{id}", missingId))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk());
    }
}
