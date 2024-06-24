package com.rental.controller;

import com.rental.entity.Payment;
import com.rental.repository.PaymentRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

  private final PaymentRepository paymentRepository;

  @Autowired
  public PaymentController(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  @GetMapping("/success/{paymentId}")
  public String paymentSuccess(@PathVariable UUID paymentId)  {
    System.out.println("Payment ID: " + paymentId);
    Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("Payment not found"));
    payment.setStatus("Completed");
    paymentRepository.save(payment);
    return "Payment successful!";
  }

  @GetMapping("/cancel/{paymentId}")
  public String paymentFailed(@PathVariable UUID paymentId) {
    System.out.println("Payment ID: " + paymentId);
    Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("Payment not found"));
    payment.setStatus("Failed");
    paymentRepository.save(payment);
    return "Payment failed. Please try again.";
  }
}
