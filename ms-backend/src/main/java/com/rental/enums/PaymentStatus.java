package com.rental.enums;

/**
 * The enum Payment method.
 */
public enum PaymentStatus {

  /**
   * The Pay at counter.
   */
  CONFIRMED("Pagamento confirmado com sucesso!"),

  /**
   * The Online payment.
   */
  CANCEL("Pagamento cancelado!");

  private final String name;

  PaymentStatus(String name) {
    this.name = name;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return this.name;
  }
}
