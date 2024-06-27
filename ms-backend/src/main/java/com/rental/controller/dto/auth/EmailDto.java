package com.rental.controller.dto.auth;

import java.util.UUID;

/**
 * The type Email dto.
 */
public class EmailDto {
  private UUID userId;
  private String emailTo;
  private String subject;
  private String text;

  /**
   * Gets user id.
   *
   * @return the user id
   */
  public UUID getUserId() {
    return userId;
  }

  /**
   * Sets user id.
   *
   * @param userId the user id
   */
  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  /**
   * Gets email to.
   *
   * @return the email to
   */
  public String getEmailTo() {
    return emailTo;
  }

  /**
   * Sets email to.
   *
   * @param emailTo the email to
   */
  public void setEmailTo(String emailTo) {
    this.emailTo = emailTo;
  }

  /**
   * Gets subject.
   *
   * @return the subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * Sets subject.
   *
   * @param subject the subject
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * Gets text.
   *
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * Sets text.
   *
   * @param text the text
   */
  public void setText(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "EmailDto{" +
        "userId=" + userId +
        ", emailTo='" + emailTo + '\'' +
        ", subject='" + subject + '\'' +
        ", text='" + text + '\'' +
        '}';
  }
}
