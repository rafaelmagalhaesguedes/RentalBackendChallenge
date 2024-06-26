package com.rental.producer;

import com.rental.controller.dto.auth.EmailDto;
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

  /**
   * Publish message email.
   *
   * @param emailDto the email DTO
   */
  public void publishMessageEmail(EmailDto emailDto) {
    rabbitTemplate.convertAndSend("", routingKey, emailDto);
  }
}
