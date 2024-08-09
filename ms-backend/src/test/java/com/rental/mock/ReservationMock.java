package com.rental.mock;

import com.rental.controller.dto.reservation.ReservationCreationDto;
import com.rental.entity.Accessory;
import com.rental.entity.Person;
import com.rental.entity.Reservation;
import com.rental.entity.Group;
import com.rental.enums.PaymentType;
import com.rental.enums.ReservationStatus;
import com.stripe.model.checkout.Session;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ReservationMock {

    public static final String URL_RESERVATION = "/reservation";

    // Mock data for Reservation 00
    public static final UUID PERSON_ID = UUID.randomUUID();
    public static final UUID GROUP_ID = UUID.randomUUID();
    public static final List<UUID> ACCESSORY_IDS = List.of(UUID.randomUUID(), UUID.randomUUID());
    public static final LocalDateTime PICKUP_DATE_TIME = LocalDateTime.now().plusDays(1);
    public static final LocalDateTime RETURN_DATE_TIME = LocalDateTime.now().plusDays(7);
    public static final PaymentType PAYMENT_TYPE = PaymentType.ONLINE_PAYMENT;
    public static final String PAYMENT_URL = "http://payment.url";

    // Mock data for Reservation 01
    private static final UUID ID_01 = UUID.randomUUID();
    private static final Person PERSON_01 = PersonMock.PERSON_01;
    private static final Group GROUP_01 = GroupMock.GROUP_01;
    private static final List<Accessory> ACCESSORIES_01 = Arrays.asList(AccessoryMock.ACCESSORY_01, AccessoryMock.ACCESSORY_02);
    private static final LocalDateTime PICKUP_DATE_TIME_01 = LocalDateTime.of(2024, 7, 1, 10, 0);
    private static final LocalDateTime RETURN_DATE_TIME_01 = LocalDateTime.of(2024, 7, 5, 10, 0);
    private static final double TOTAL_AMOUNT_01 = 500.0;
    private static final int TOTAL_DAYS_01 = 4;
    private static final PaymentType PAYMENT_TYPE_01 = PaymentType.ONLINE_PAYMENT;
    private static final ReservationStatus RESERVATION_STATUS_01 = ReservationStatus.PENDING;
    private static final LocalDateTime CREATED_DATE_01 = LocalDateTime.now();

    // Mock data for Reservation 02
    private static final UUID ID_02 = UUID.randomUUID();
    private static final Person PERSON_02 = PersonMock.PERSON_02;
    private static final Group GROUP_02 = GroupMock.GROUP_02;
    private static final List<Accessory> ACCESSORIES_02 = List.of(AccessoryMock.ACCESSORY_02);
    private static final LocalDateTime PICKUP_DATE_TIME_02 = LocalDateTime.of(2024, 8, 1, 10, 0);
    private static final LocalDateTime RETURN_DATE_TIME_02 = LocalDateTime.of(2024, 8, 3, 10, 0);
    private static final double TOTAL_AMOUNT_02 = 300.0;
    private static final int TOTAL_DAYS_02 = 2;
    private static final PaymentType PAYMENT_TYPE_02 = PaymentType.ONLINE_PAYMENT;
    private static final ReservationStatus RESERVATION_STATUS_02 = ReservationStatus.CONFIRMED;
    private static final LocalDateTime CREATED_DATE_02 = LocalDateTime.now();

    // Mock reservation create
    public static final ReservationCreationDto RESERVATION_CREATION = new ReservationCreationDto(
            PERSON_ID,
            GROUP_ID,
            ACCESSORY_IDS,
            PICKUP_DATE_TIME,
            RETURN_DATE_TIME,
            TOTAL_AMOUNT_01,
            TOTAL_DAYS_01
    );

    // Mock reservations
    public static final Reservation RESERVATION_01 = createReservation(ID_01, PERSON_01, GROUP_01, ACCESSORIES_01, PICKUP_DATE_TIME_01,
            RETURN_DATE_TIME_01, TOTAL_AMOUNT_01, TOTAL_DAYS_01, String.valueOf(PAYMENT_TYPE_01), RESERVATION_STATUS_01, CREATED_DATE_01);

    public static final Reservation RESERVATION_02 = createReservation(ID_02, PERSON_02, GROUP_02, ACCESSORIES_02, PICKUP_DATE_TIME_02,
            RETURN_DATE_TIME_02, TOTAL_AMOUNT_02, TOTAL_DAYS_02, String.valueOf(PAYMENT_TYPE_02), RESERVATION_STATUS_02, CREATED_DATE_02);

    private static Reservation createReservation(UUID id, Person person, Group group, List<Accessory> accessories, LocalDateTime pickupDateTime,
                                                 LocalDateTime returnDateTime, double totalAmount, int totalDays, String paymentType,
                                                 ReservationStatus reservationStatus, LocalDateTime createdDate) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setPerson(person);
        reservation.setGroup(group);
        reservation.setAccessories(accessories);
        reservation.setPickupDateTime(pickupDateTime);
        reservation.setReturnDateTime(returnDateTime);
        reservation.setTotalAmount(totalAmount);
        reservation.setTotalDays(totalDays);
        reservation.setPaymentType(PaymentType.valueOf(paymentType));
        reservation.setReservationStatus(reservationStatus);
        reservation.setCreatedDate(createdDate);
        return reservation;
    }

    // Mock session
    public static Session createMockSession() {
        Session session = new Session();
        session.setUrl("http://localhost:8080/payment/success");
        return session;
    }
}
