package com.rental.service;

import com.rental.entity.Payment;
import com.rental.entity.Reservation;
import com.rental.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;

  @Autowired
  public PaymentService(PaymentRepository paymentRepository, @Value("${stripe.api.key}") String stripeApiKey) {
    this.paymentRepository = paymentRepository;
    Stripe.apiKey = stripeApiKey;
  }

  @Transactional
  public Session createCheckoutSession(Double amount, String successUrl, String cancelUrl, Reservation reservation) throws StripeException {
    Payment payment = new Payment();
    payment.setReservation(reservation);
    payment.setAmount(amount);
    payment.setPaymentDate(LocalDateTime.now());

    paymentRepository.save(payment);

    return getSession(amount, successUrl, cancelUrl, payment);
  }

  private Session getSession(Double amount, String successUrl, String cancelUrl, Payment payment) throws StripeException {
    SessionCreateParams params = SessionCreateParams.builder()
        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
        .addLineItem(
            SessionCreateParams.LineItem.builder()
                .setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("usd")
                        .setUnitAmount((long) (amount * 100)) // amount in cents
                        .setProductData(
                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName("Reservation Payment")
                                .build())
                        .build())
                .setQuantity(1L)
                .build())
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .setSuccessUrl(successUrl + "/" + payment.getId())
        .setCancelUrl(cancelUrl + "/" + payment.getId())
        .build();

    return Session.create(params);
  }
}
