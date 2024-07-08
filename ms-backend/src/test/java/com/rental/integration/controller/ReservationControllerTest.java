package com.rental.integration.controller;

import static com.rental.mock.ReservationMock.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.security.JwtFilter;
import com.rental.utils.TestSecurityConfig;
import com.rental.controller.ReservationController;
import com.rental.controller.dto.reservation.ReservationCreationDto;
import com.rental.controller.dto.reservation.ReservationDto;
import com.rental.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(ReservationController.class)
@Import(TestSecurityConfig.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    public void testCreateReservation() throws Exception {
        // Arrange
        ReservationDto reservationDto = ReservationDto.fromEntity(RESERVATION_01, PAYMENT_URL);

        when(reservationService.createReservation(Mockito.any(ReservationCreationDto.class)))
                .thenReturn(reservationDto);

        // Act + Assert
        mockMvc.perform(post(URL_RESERVATION)
                        .content(objectMapper.writeValueAsString(RESERVATION_CREATION))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllReservations() throws Exception {
        // Arrange
        when(reservationService.getAllReservations(0, 10))
                .thenReturn(List.of(RESERVATION_01, RESERVATION_02));

        // Act + Assert
        mockMvc.perform(get(URL_RESERVATION)
                        .param("pageNumber", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk());
    }
}
