package com.rental.integration.controller;

import static com.rental.mock.PersonMock.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.utils.TestSecurityConfig;
import com.rental.service.PersonService;
import com.rental.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@WebMvcTest(PersonController.class)
@Import(TestSecurityConfig.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    @MockBean
    private TokenService tokenService;

    @Test
    public void testCreatePerson() throws Exception {
        when(personService.createPerson(PERSON_CREATION)).thenReturn(PERSON_CREATION);

        mockMvc.perform(post("/persons")
                        .content(objectMapper.writeValueAsString(PERSON_CREATION))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value(PERSON_CREATION.getFullName()))
                .andExpect(jsonPath("$.username").value(PERSON_CREATION.getUsername()))
                .andExpect(jsonPath("$.email").value(PERSON_CREATION.getEmail()))
                .andExpect(jsonPath("$.role").value(PERSON_CREATION.getRole().getName()));
    }

    @Test
    public void testRetrievalPersonById() throws Exception {
        UUID personId = UUID.randomUUID();
        when(personService.getPersonById(personId))
                .thenReturn(PERSON_01);

        mockMvc.perform(get("/persons/{id}", personId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(PERSON_01.getId().toString()))
                .andExpect(jsonPath("$.fullName").value(PERSON_01.getFullName()))
                .andExpect(jsonPath("$.username").value(PERSON_01.getUsername()))
                .andExpect(jsonPath("$.email").value(PERSON_01.getEmail()))
                .andExpect(jsonPath("$.role").value(PERSON_01.getRole().getName()));
    }

    @Test
    public void testDeletePerson() throws Exception {
        UUID id = UUID.randomUUID();
        when(personService.deletePerson(id)).thenReturn(PERSON_01);

        mockMvc.perform(delete("/persons/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
