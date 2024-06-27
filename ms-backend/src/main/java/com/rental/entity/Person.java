package com.rental.entity;

import com.rental.security.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The type Person.
 */
@Entity
@Table(name = "persons")
public class Person implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String fullName;

  private String username;

  @Column(unique = true)
  private String email;

  private String password;

  private Role role;

  /**
   * Instantiates a new Person.
   */
  public Person() { }

  /**
   * Instantiates a new Person.
   *
   * @param fullName the full name
   * @param username the username
   * @param email    the email
   * @param password the password
   * @param role     the role
   */
  public Person(String fullName, String username, String email, String password, Role role) {
    this.fullName = fullName;
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  /**
   * Instantiates a new Person.
   *
   * @param fullName the full name
   * @param username the username
   * @param email    the email
   * @param password the password
   */
  public Person(String fullName, String username, String email, String password) {
    this.fullName = fullName;
    this.username = username;
    this.email = email;
    this.password = password;
  }

  /**
   * Instantiates a new Person.
   *
   * @param fullName the full name
   * @param username the username
   * @param email    the email
   */
  public Person(String fullName, String username, String email) {
    this.fullName = fullName;
    this.username = username;
    this.email = email;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public UUID getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(UUID id) {
    this.id = id;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return username;
  }

  /**
   * Sets name.
   *
   * @param fullName the full name
   */
  public void setName(String fullName) {
    this.username = fullName;
  }

  /**
   * Gets email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets email.
   *
   * @param email the email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets full name.
   *
   * @return the full name
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * Sets full name.
   *
   * @param fullName the full name
   */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  /**
   * Sets password.
   *
   * @param password the password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets role.
   *
   * @return the role
   */
  public Role getRole() {
    return role;
  }

  /**
   * Sets role.
   *
   * @param role the role
   */
  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.getName()));
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  /**
   * Sets username.
   *
   * @param username the username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
