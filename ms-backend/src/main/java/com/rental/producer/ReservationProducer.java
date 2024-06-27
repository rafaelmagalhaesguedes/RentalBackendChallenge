package com.rental.producer;

import com.rental.controller.dto.auth.EmailDto;
import com.rental.entity.Person;
import com.rental.entity.Reservation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type Reservation producer.
 */
@Component
public class ReservationProducer {

  final RabbitTemplate rabbitTemplate;

  /**
   * Instantiates a new Reservation producer.
   *
   * @param rabbitTemplate the rabbit template
   */
  public ReservationProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Value(value = "${broker.queue.email.name}")
  private String routingKey;

  public void publishMessageEmail(Person person, Reservation reservation) {
    EmailDto emailDto = new EmailDto();
    emailDto.setUserId(person.getId());
    emailDto.setEmailTo(person.getEmail());
    emailDto.setSubject("Reserva Confirmada");

    String emailText = String.format(
        "Olá %s,\n\n" +
            "Sua reserva foi confirmada com sucesso!\n\n" +
            "Detalhes da reserva:\n" +
            "- Data de início: %s\n" +
            "- Data de término: %s\n" +
            "- Total: %s\n\n" +
            "- Pagamento: %s\n\n" +
            "Obrigado por escolher nossos serviços.",
        person.getFullName(),
        reservation.getPickupDateTime(),
        reservation.getReturnDateTime(),
        reservation.getTotalAmount(),
        reservation.getPaymentMethod()
    );

    emailDto.setText(emailText);

    rabbitTemplate.convertAndSend("", routingKey, emailDto);
  }
}
