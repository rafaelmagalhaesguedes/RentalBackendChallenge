package com.rental.mock;

import com.rental.entity.Accessory;
import com.rental.entity.Person;
import com.rental.entity.Reservation;
import com.rental.entity.Group;
import com.rental.enums.ReservationStatus;
import com.stripe.model.checkout.Session;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ReservationMock {

    // Mock data for Reservation 01
    private static final UUID ID_01 = UUID.randomUUID();
    private static final Person PERSON_01 = PersonMock.PERSON_01;
    private static final Group GROUP_01 = GroupMock.GROUP_01;
    private static final List<Accessory> ACCESSORIES_01 = Arrays.asList(AccessoryMock.ACCESSORY_01, AccessoryMock.ACCESSORY_02);
    private static final LocalDateTime PICKUP_DATE_TIME_01 = LocalDateTime.of(2024, 7, 1, 10, 0);
    private static final LocalDateTime RETURN_DATE_TIME_01 = LocalDateTime.of(2024, 7, 5, 10, 0);
    private static final double TOTAL_AMOUNT_01 = 500.0;
    private static final int TOTAL_DAYS_01 = 4;
    private static final String PAYMENT_TYPE_01 = "Online";
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
    private static final String PAYMENT_TYPE_02 = "Offline";
    private static final ReservationStatus RESERVATION_STATUS_02 = ReservationStatus.CONFIRMED;
    private static final LocalDateTime CREATED_DATE_02 = LocalDateTime.now();

    // Mock reservations
    public static final Reservation RESERVATION_01 = createReservation(ID_01, PERSON_01, GROUP_01, ACCESSORIES_01, PICKUP_DATE_TIME_01,
            RETURN_DATE_TIME_01, TOTAL_AMOUNT_01, TOTAL_DAYS_01, PAYMENT_TYPE_01, RESERVATION_STATUS_01, CREATED_DATE_01);

    public static final Reservation RESERVATION_02 = createReservation(ID_02, PERSON_02, GROUP_02, ACCESSORIES_02, PICKUP_DATE_TIME_02,
            RETURN_DATE_TIME_02, TOTAL_AMOUNT_02, TOTAL_DAYS_02, PAYMENT_TYPE_02, RESERVATION_STATUS_02, CREATED_DATE_02);

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
        reservation.setPaymentType(paymentType);
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
