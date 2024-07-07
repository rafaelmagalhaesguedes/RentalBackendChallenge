package com.rental.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.TestSecurityConfig;
import com.rental.controller.AuthController;
import com.rental.controller.dto.auth.AuthDto;
import com.rental.entity.Person;
import com.rental.security.Role;
import com.rental.service.PersonService;
import com.rental.service.TokenService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(TestSecurityConfig.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private TokenService tokenService;

    @Captor
    ArgumentCaptor<UsernamePasswordAuthenticationToken> authCaptor;

    @Test
    public void testLoginSuccess() throws Exception {
        AuthDto authDto = new AuthDto("test@test.com", "password");
        Person person = new Person(UUID.randomUUID(), "Test User", "test", "test@test.com", "password", Role.USER);
        String token = "mock-token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(person, null, person.getAuthorities()));
        when(tokenService.generateToken(any(Person.class))).thenReturn(token);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    public void testLoginFailure() throws Exception {
        AuthDto authDto = new AuthDto("test@test.com", "wrong-password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid email or password."));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.token").value("Invalid email or password."));
    }
}

