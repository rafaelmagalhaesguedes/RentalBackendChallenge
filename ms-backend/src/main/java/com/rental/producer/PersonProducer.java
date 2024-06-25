package com.rental.producer;

import com.rental.controller.dto.auth.EmailDto;
import com.rental.entity.Person;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type User producer.
 */
@Component
public class PersonProducer {

  final RabbitTemplate rabbitTemplate;

  /**
   * Instantiates a new User producer.
   *
   * @param rabbitTemplate the rabbit template
   */
  public PersonProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Value(value = "${broker.queue.email.name}")
  private String routingKey;

  /**
   * Publish message email.
   *
   * @param person the person
   */
  public void publishMessageEmail(Person person) {
    EmailDto emailDto = new EmailDto();
    emailDto.setUserId(person.getId());
    emailDto.setEmailTo(person.getEmail());
    emailDto.setSubject("Registration Completed Successfully!");

    String emailText = String.format(
        "Dear %s,\n\n" +
            "We are thrilled to welcome you to [Your Car Rental Company]! Your registration has been successfully completed, and we're excited to have you as a valued member of our community.\n\n" +
            "Here’s what to expect next:\n\n" +
            "1. Account Activation:\n" +
            "Your account is now active and ready to use. You can log in using your registered email and the password you created during the registration process.\n\n" +
            "2. Personalize Your Experience:\n" +
            "Take a moment to complete your profile by adding more details about your preferences and travel needs. This helps us tailor the experience to better serve you.\n\n" +
            "3. Explore Our Services:\n" +
            "As a member of [Your Car Rental Company], you have access to a wide range of services designed to enhance your car rental experience. Some of these include:\n" +
            "- Flexible Booking Options: Choose from a variety of vehicles to suit your needs, from economy cars to luxury models.\n" +
            "- Convenient Pickup and Drop-off: Multiple locations to make your rental experience seamless.\n" +
            "- Special Offers: Enjoy exclusive discounts and deals available only to our registered members.\n\n" +
            "4. Stay Updated:\n" +
            "Keep an eye on your inbox for our newsletters and updates. We’ll share tips, news, and special offers to help you get the most out of our services.\n\n" +
            "5. Get Support:\n" +
            "If you have any questions or need assistance, our support team is here to help. You can reach out to us at [Support Email] or visit our Help Center [link to Help Center].\n\n" +
            "Ready to hit the road?\n\n" +
            "Log in now to start exploring our fleet and make your first booking. Thank you for choosing [Your Car Rental Company]. We look forward to serving you!\n\n" +
            "Safe travels,\n\n" +
            "The [Your Car Rental Company] Team",
        person.getFullName()
    );

    emailDto.setText(emailText);

    rabbitTemplate.convertAndSend("", routingKey, emailDto);
  }
}
