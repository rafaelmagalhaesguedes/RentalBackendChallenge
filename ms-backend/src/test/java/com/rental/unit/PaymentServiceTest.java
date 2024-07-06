package com.rental.unit;

import com.rental.entity.Payment;
import com.rental.enums.PaymentStatus;
import com.rental.repository.PaymentRepository;
import com.rental.service.PaymentService;
import com.rental.service.exception.PaymentNotFoundException;
import com.rental.mock.PaymentMock;
import com.stripe.Stripe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Stripe.apiKey = stripeApiKey;
    }

    @Test
    public void testPaymentSuccess() throws PaymentNotFoundException {
        // Arrange
        UUID paymentId = PaymentMock.PAYMENT_ID_01;
        Payment payment = PaymentMock.PAYMENT_01;

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        // Act
        Payment updatedPayment = paymentService.paymentSuccess(paymentId);

        // Assert
        assertEquals(PaymentStatus.CONFIRMED, updatedPayment.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    public void testPaymentCancel() throws PaymentNotFoundException {
        // Arrange
        UUID paymentId = PaymentMock.PAYMENT_ID_01;
        Payment payment = PaymentMock.PAYMENT_01;

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        // Act
        Payment updatedPayment = paymentService.paymentCancel(paymentId);

        // Assert
        assertEquals(PaymentStatus.CANCEL, updatedPayment.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    public void testPaymentSuccess_NotFoundException() {
        // Arrange
        UUID paymentId = UUID.randomUUID();

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(PaymentNotFoundException.class, () -> paymentService.paymentSuccess(paymentId));
    }

    @Test
    public void testPaymentCancel_NotFoundException() {
        // Arrange
        UUID paymentId = UUID.randomUUID();

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(PaymentNotFoundException.class, () -> paymentService.paymentCancel(paymentId));
    }
}
