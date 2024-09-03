package com.rental.controller.dto.auth;

/**
 * The type Email dto.
 */
public class EmailDto {
  private String userName;
  private String emailTo;
  private String subject;
  private String text;

  public String getUserId() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmailTo() {
    return emailTo;
  }

  public void setEmailTo(String emailTo) {
    this.emailTo = emailTo;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "EmailDto{" +
        "userName=" + userName +
        ", emailTo='" + emailTo + '\'' +
        ", subject='" + subject + '\'' +
        ", text='" + text + '\'' +
        '}';
  }
}
