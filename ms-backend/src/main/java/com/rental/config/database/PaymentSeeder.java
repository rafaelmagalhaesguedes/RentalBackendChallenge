package com.rental.config.database;

import com.rental.entity.Payment;
import com.rental.entity.Reservation;
import com.rental.repository.PaymentRepository;
import com.rental.repository.ReservationRepository;
import com.rental.enums.PaymentStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PaymentSeeder implements CommandLineRunner {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public PaymentSeeder(PaymentRepository paymentRepository, ReservationRepository reservationRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seed();
    }

    public void seed() {
        List<Payment> payments = new ArrayList<>();

        Reservation reservation1 = reservationRepository.findByPersonEmail("joao.silva@example.com").orElseThrow();
        Reservation reservation2 = reservationRepository.findByPersonEmail("maria.oliveira@example.com").orElseThrow();

        payments.add(new Payment(UUID.randomUUID(), reservation1, 200.00, PaymentStatus.CONFIRMED, LocalDateTime.now(), "Online"));
        payments.add(new Payment(UUID.randomUUID(), reservation2, 220.00, PaymentStatus.CONFIRMED, LocalDateTime.now(), "Offline"));

        paymentRepository.saveAll(payments);
    }
}
