package com.rental.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.controller.dto.person.PersonCreationDto;
import com.rental.repository.PersonRepository;
import com.rental.security.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class PersonIntegrationTest {

  @Autowired
  PersonRepository personRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  MockMvc mockMvc;

  @Container
  public static MySQLContainer MYSQL_CONTAINER = new MySQLContainer("mysql:8.0.32")
      .withDatabaseName("rental");

  @DynamicPropertySource
  public static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
  }

  @Test
  public void testPersonCreation() throws Exception {
    PersonCreationDto personDto = new PersonCreationDto(
    "Rafael Guedes",
    "rafa",
    "rafa@gmail.com",
    "password",
        Role.MANAGER
    );

    mockMvc.perform(post("/persons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(personDto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.fullName").value("Rafael Guedes"))
        .andExpect(jsonPath("$.username").value("rafa"))
        .andExpect(jsonPath("$.email").value("rafa@gmail.com"))
        .andExpect(jsonPath("$.role").value("MANAGER"));
  }
}

