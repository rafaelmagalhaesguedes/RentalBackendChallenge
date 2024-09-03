//package com.rental.mock;
//
//import com.rental.entity.Payment;
//import com.rental.entity.Reservation;
//import com.rental.enums.PaymentStatus;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//public class PaymentMock {
//
//    // Mock data for Payment 01
//    public static final UUID PAYMENT_ID_01 = UUID.randomUUID();
//    public static final Reservation RESERVATION_01 = ReservationMock.RESERVATION_01;
//    public static final Double AMOUNT_01 = 500.0;
//    public static final LocalDateTime PAYMENT_DATE_01 = LocalDateTime.of(2024, 7, 1, 12, 0);
//    public static final PaymentStatus STATUS_01 = PaymentStatus.CANCEL;
//
//    // Mock data for Payment 02
//    public static final UUID PAYMENT_ID_02 = UUID.randomUUID();
//    public static final Reservation RESERVATION_02 = ReservationMock.RESERVATION_02;
//    public static final Double AMOUNT_02 = 300.0;
//    public static final LocalDateTime PAYMENT_DATE_02 = LocalDateTime.of(2024, 8, 1, 12, 0);
//    public static final PaymentStatus STATUS_02 = PaymentStatus.CONFIRMED;
//
//    public static Payment createPayment(UUID id, Reservation reservation, Double amount, LocalDateTime paymentDate, PaymentStatus status) {
//        Payment payment = new Payment();
//        payment.setId(id);
//        payment.setReservation(reservation);
//        payment.setAmount(amount);
//        payment.setPaymentDate(paymentDate);
//        payment.setStatus(status);
//        return payment;
//    }
//
//    // Mock payments
//    public static final Payment PAYMENT_01 = createPayment(PAYMENT_ID_01, RESERVATION_01, AMOUNT_01, PAYMENT_DATE_01, STATUS_01);
//    public static final Payment PAYMENT_02 = createPayment(PAYMENT_ID_02, RESERVATION_02, AMOUNT_02, PAYMENT_DATE_02, STATUS_02);
//}
