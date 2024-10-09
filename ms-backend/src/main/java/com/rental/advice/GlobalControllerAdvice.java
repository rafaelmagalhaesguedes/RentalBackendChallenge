package com.rental.advice;

import com.rental.service.exception.*;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * The type Global controller advice.
 */
@ControllerAdvice
public class GlobalControllerAdvice {

  /**
   * Handle constraint violation exceptions.
   *
   * @param ex the ConstraintViolationException
   * @return the response entity with errors details
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, Object>> handleConstraintViolationExceptions(ConstraintViolationException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    body.put("statusCode", HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle validation exceptions.
   *
   * @param ex the MethodArgumentNotValidException
   * @param request the web request
   * @return the response entity with errors details
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }

    Map<String, Object> body = new HashMap<>();
    body.put("statusCode", HttpStatus.UNPROCESSABLE_ENTITY.value());
    body.put("errors", errors);

    return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  /**
   * Handle NotFoundException response entity.
   *
   * @param ex the NotFoundException
   * @return the response entity
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    body.put("statusCode", HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  /**
   * Handle CustomerNotFoundException response entity.
   *
   * @param ex the CustomerNotFoundException
   * @return the response entity
   */
  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleCustomerNotFoundException(CustomerNotFoundException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    body.put("statusCode", HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  /**
   * Handle GroupNotFoundException response entity.
   *
   * @param ex the GroupNotFoundException
   * @return the response entity
   */
  @ExceptionHandler(GroupNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleGroupNotFoundException(GroupNotFoundException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    body.put("statusCode", HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  /**
   * Handle PaymentNotFoundException response entity.
   *
   * @param ex the PaymentNotFoundException
   * @return the response entity
   */
  @ExceptionHandler(PaymentNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handlePaymentNotFoundException(PaymentNotFoundException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    body.put("statusCode", HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  /**
   * Handle PersonNotFoundException response entity.
   *
   * @param ex the PersonNotFoundException
   * @return the response entity
   */
  @ExceptionHandler(PersonNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handlePersonNotFoundException(PersonNotFoundException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    body.put("statusCode", HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  /**
   * Handle PersonExistingException response entity.
   *
   * @param ex the PersonExistingException
   * @return the response entity
   */
  @ExceptionHandler(PersonExistingException.class)
  public ResponseEntity<Map<String, Object>> handlePersonExistingException(PersonExistingException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    body.put("statusCode", HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  /**
   * Handle ReservationNotFoundException response entity.
   *
   * @param ex the ReservationNotFoundException
   * @return the response entity
   */
  @ExceptionHandler(ReservationNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleReservationNotFoundException(ReservationNotFoundException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    body.put("statusCode", HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  /**
   * Handle VehicleNotFoundException response entity.
   *
   * @param ex the VehicleNotFoundException
   * @return the response entity
   */
  @ExceptionHandler(VehicleNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleVehicleNotFoundException(VehicleNotFoundException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    body.put("statusCode", HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }
}