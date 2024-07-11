package com.email.service;

import com.email.entity.Email;
import com.email.enums.StatusEmail;
import com.email.repository.EmailRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * The type Email service.
 */
@Service
public class EmailService {

  /**
   * The Email repository.
   */
  final EmailRepository emailRepository;
  /**
   * The Java mail sender.
   */
  final JavaMailSender javaMailSender;

  /**
   * Instantiates a new Email service.
   *
   * @param emailRepository the email repository
   * @param javaMailSender  the java mail sender
   */
  public EmailService(EmailRepository emailRepository, JavaMailSender javaMailSender) {
    this.emailRepository = emailRepository;
    this.javaMailSender = javaMailSender;
  }

  @Value(value = "${spring.mail.username}")
  private String emailFrom;

  @Value(value = "${admin.email}")
  private String adminEmail;

  @Value(value = "${manager.email}")
  private String managerEmail;

  /**
   * Send email.
   *
   * @param email the email
   */
  @Transactional
  public void sendEmail(Email email) {
    sendUserEmail(email);
    sendAdminAndManagerEmail(email);
  }

  private void sendUserEmail(Email email) {
    email.setSendDateEmail(LocalDateTime.now());
    email.setEmailFrom(emailFrom);

    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(email.getEmailTo());
      message.setSubject(email.getSubject());
      message.setText(email.getText());
      javaMailSender.send(message);

      email.setStatusEmail(StatusEmail.SENT);
    } catch (MailException e) {
      email.setStatusEmail(StatusEmail.ERROR);
    }

    emailRepository.save(email);
  }

  private void sendAdminAndManagerEmail(Email email) {
    Email adminAndManagerEmailEntity = new Email();
    adminAndManagerEmailEntity.setSendDateEmail(LocalDateTime.now());
    adminAndManagerEmailEntity.setEmailFrom(emailFrom);
    adminAndManagerEmailEntity.setEmailTo(adminEmail);
    adminAndManagerEmailEntity.setSubject("New Reservation Created");
    adminAndManagerEmailEntity.setText("A new reservation has been created with the following details:\n\n" +
            "User: " + email.getEmailTo() + "\n" +
            "Subject: " + email.getSubject() + "\n" +
            "Message: " + email.getText());

    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(adminEmail, managerEmail);  // Envia para o administrador e o gerente
      message.setSubject(adminAndManagerEmailEntity.getSubject());
      message.setText(adminAndManagerEmailEntity.getText());
      javaMailSender.send(message);

      adminAndManagerEmailEntity.setStatusEmail(StatusEmail.SENT);
    } catch (MailException e) {
      adminAndManagerEmailEntity.setStatusEmail(StatusEmail.ERROR);
    }

    emailRepository.save(adminAndManagerEmailEntity);
  }
}
