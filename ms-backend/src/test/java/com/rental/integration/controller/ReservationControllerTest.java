//package com.rental.integration.controller;
//
//import static com.rental.mock.ReservationMock.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.rental.security.JwtFilter;
//import com.rental.utils.TestSecurityConfig;
//import com.rental.controller.ReservationController;
//import com.rental.controller.dto.reservation.ReservationCreationDto;
//import com.rental.controller.dto.reservation.ReservationDto;
//import com.rental.controller.dto.reservation.ReservationPaymentDto;
//import com.rental.controller.dto.reservation.ReservationReadDto;
//import com.rental.service.ReservationService;
//import com.rental.service.exception.PersonNotFoundException;
//import com.rental.service.exception.GroupNotFoundException;
//import com.rental.service.exception.ReservationNotFoundException;
//import com.stripe.exception.StripeException;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//import java.util.UUID;
//
//@WebMvcTest(ReservationController.class)
//@Import(TestSecurityConfig.class)
//public class ReservationControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private ReservationService reservationService;
//
//    @MockBean
//    private JwtFilter jwtFilter;
//
//    @Test
//    public void testCreateOnlineReservation() throws Exception {
//        // Arrange
//        ReservationPaymentDto reservationPaymentDto = ReservationPaymentDto.fromEntity(RESERVATION_01, PAYMENT_URL);
//
//        when(reservationService.createReservationWithOnlinePayment(Mockito.any(ReservationCreationDto.class)))
//                .thenReturn(reservationPaymentDto);
//
//        // Act + Assert
//        mockMvc.perform(post("/reservation/payment/online")
//                        .content(objectMapper.writeValueAsString(RESERVATION_CREATION))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testCreateStoreReservation() throws Exception {
//        // Arrange
//        ReservationDto reservationDto = ReservationDto.fromEntity(RESERVATION_01);
//
//        when(reservationService.createReservationWithStorePayment(Mockito.any(ReservationCreationDto.class)))
//                .thenReturn(reservationDto);
//
//        // Act + Assert
//        mockMvc.perform(post("/reservation/payment/store")
//                        .content(objectMapper.writeValueAsString(RESERVATION_CREATION))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testGetReservationById() throws Exception {
//        // Arrange
//        UUID reservationId = UUID.randomUUID();
//        ReservationReadDto reservationReadDto = ReservationReadDto.fromEntity(RESERVATION_01);
//
//        when(reservationService.getReservationById(reservationId))
//                .thenReturn(RESERVATION_01);
//
//        // Act + Assert
//        mockMvc.perform(get("/reservation/" + reservationId))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testGetAllReservations() throws Exception {
//        // Arrange
//        when(reservationService.getAllReservations(0, 10))
//                .thenReturn(List.of(RESERVATION_01, RESERVATION_02));
//
//        // Act + Assert
//        mockMvc.perform(get("/reservation")
//                        .param("pageNumber", "0")
//                        .param("pageSize", "10"))
//                .andExpect(status().isOk());
//    }
//}
