package com.email;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.email.entity.Email;
import com.email.enums.StatusEmail;
import com.email.repository.EmailRepository;

import com.email.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(emailService, "emailFrom", "user@example.com");
        ReflectionTestUtils.setField(emailService, "adminEmail", "admin@example.com");
        ReflectionTestUtils.setField(emailService, "managerEmail", "manager@example.com");
    }

    @Test
    public void testSendEmail_Success() {
        // Arrange
        Email email = new Email();
        email.setEmailTo("test@example.com");
        email.setSubject("Test Subject");
        email.setText("Test Text");

        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        when(emailRepository.save(any(Email.class))).thenReturn(email);

        // Act
        emailService.sendEmail(email);

        // Assert
        verify(javaMailSender, times(2)).send(any(SimpleMailMessage.class));
        verify(emailRepository, times(2)).save(any(Email.class));
        assertThat(email.getStatusEmail()).isEqualTo(StatusEmail.SENT);
        assertThat(email.getSendDateEmail()).isNotNull();
        assertThat(email.getEmailFrom()).isEqualTo("user@example.com");
    }

    @Test
    public void testSendEmail_Failure() {
        // Arrange
        Email email = new Email();
        email.setEmailTo("test@example.com");
        email.setSubject("Test Subject");
        email.setText("Test Text");

        doThrow(new MailException("Mail sending failed") {}).when(javaMailSender).send(any(SimpleMailMessage.class));
        when(emailRepository.save(any(Email.class))).thenReturn(email);

        // Act
        emailService.sendEmail(email);

        // Assert
        verify(javaMailSender, times(2)).send(any(SimpleMailMessage.class));
        verify(emailRepository, times(2)).save(any(Email.class));
        assertThat(email.getStatusEmail()).isEqualTo(StatusEmail.ERROR);
        assertThat(email.getSendDateEmail()).isNotNull();
        assertThat(email.getEmailFrom()).isEqualTo("user@example.com");
    }
}
