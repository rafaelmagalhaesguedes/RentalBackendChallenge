package com.rental.controller;

import com.rental.entity.Payment;
import com.rental.repository.PaymentRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {

  private final PaymentRepository paymentRepository;

  @Autowired
  public PaymentController(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  @GetMapping("/success/{paymentId}")
  public String paymentSuccess(@PathVariable UUID paymentId, Model model) {
    Payment payment = paymentRepository.findById(paymentId)
        .orElseThrow(() -> new RuntimeException("Payment not found"));
    payment.setStatus("Completed");
    paymentRepository.save(payment);

    // Add payment details to the model
    model.addAttribute("payment", payment);
    // Assuming you have a Reservation object associated with Payment
    model.addAttribute("reservation", payment.getReservation());

    return "payment-success";
  }

  @GetMapping("/cancel/{paymentId}")
  public String paymentFailed(@PathVariable UUID paymentId, Model model) {
    Payment payment = paymentRepository.findById(paymentId)
        .orElseThrow(() -> new RuntimeException("Payment not found"));
    payment.setStatus("Failed");
    paymentRepository.save(payment);

    // Add payment details to the model
    model.addAttribute("payment", payment);
    return "payment-failed";
  }
}

