package com.rental.controller;

import com.rental.entity.Payment;
import com.rental.enums.Status;
import com.rental.producer.ReservationProducer;
import com.rental.repository.PaymentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  private final PaymentRepository paymentRepository;

  /**
   * Instantiates a new Payment controller.
   *
   * @param paymentRepository the payment repository
   */
  @Autowired
  public PaymentController(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  /**
   * Payment success string.
   *
   * @param paymentId the payment id
   * @param model     the model
   * @return the string
   */
  @GetMapping("/success/{paymentId}")
  @Operation(summary = "Payment success", description = "Handles successful payment by updating the status and returning details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Payment processed successfully"),
      @ApiResponse(responseCode = "404", description = "Payment not found")
  })
  public String paymentSuccess(@PathVariable UUID paymentId, Model model) {
    Payment payment = paymentRepository.findById(paymentId)
        .orElseThrow(() -> new RuntimeException("Payment not found"));
    payment.setStatus(Status.CONFIRMED);
    paymentRepository.save(payment);

    // Add payment details to the model
    model.addAttribute("payment", payment);
    model.addAttribute("reservation", payment.getReservation());

    return "payment-success";
  }

  /**
   * Payment failed string.
   *
   * @param paymentId the payment id
   * @param model     the model
   * @return the string
   */
  @GetMapping("/cancel/{paymentId}")
  @Operation(summary = "Payment failed", description = "Handles failed payment by updating the status and returning details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Payment processed as failed"),
      @ApiResponse(responseCode = "404", description = "Payment not found")
  })
  public String paymentFailed(@PathVariable UUID paymentId, Model model) {
    Payment payment = paymentRepository.findById(paymentId)
        .orElseThrow(() -> new RuntimeException("Payment not found"));
    payment.setStatus(Status.CANCELLED);
    paymentRepository.save(payment);

    // Add payment details to the model
    model.addAttribute("payment", payment);

    return "payment-failed";
  }
}
