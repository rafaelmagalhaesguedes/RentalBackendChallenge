package com.rental.controller;

import com.rental.controller.dto.payment.PaymentResponseDto;
import com.rental.entity.Payment;
import com.rental.enums.PaymentStatus;
import com.rental.enums.ReservationStatus;
import com.rental.repository.PaymentRepository;
import com.rental.service.PaymentService;
import com.rental.service.exception.PaymentNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Payment controller.
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

  private final PaymentService paymentService;

  /**
   * Instantiates a new Payment controller.
   *
   * @param paymentService the payment service
   */
  @Autowired
  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  /**
   * Payment success string.
   *
   * @param paymentId the payment id
   * @return the string
   */
  @GetMapping("/success/{paymentId}")
  @Operation(summary = "Payment success", description = "Handles successful payment by updating the reservationStatus and returning details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Payment processed successfully"),
      @ApiResponse(responseCode = "404", description = "Payment not found")
  })
  public PaymentResponseDto paymentSuccess(@PathVariable UUID paymentId)
      throws PaymentNotFoundException {
    return PaymentResponseDto.fromEntity(
        paymentService.paymentSuccess(paymentId)
    );
  }

  /**
   * Payment failed string.
   *
   * @param paymentId the payment id
   * @return the string
   */
  @GetMapping("/cancel/{paymentId}")
  @Operation(summary = "Payment failed", description = "Handles failed payment by updating the reservationStatus and returning details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Payment processed as failed"),
      @ApiResponse(responseCode = "404", description = "Payment not found")
  })
  public PaymentResponseDto paymentFailed(@PathVariable UUID paymentId) throws PaymentNotFoundException {
    return PaymentResponseDto.fromEntity(
        paymentService.paymentCancel(paymentId)
    );
  }
}
