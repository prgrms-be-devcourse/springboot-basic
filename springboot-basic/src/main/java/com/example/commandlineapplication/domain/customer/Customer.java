package com.example.commandlineapplication.domain.customer;

import java.util.UUID;

public class Customer {

  private static final int CUSTOMER_NAME_LIMIT = 30;
  private static final int CUSTOMER_EMAIL_LIMIT = 50;
  private final UUID customerId;
  private String customerName;
  private String customerEmail;

  public Customer(UUID customerId, String customerName, String customerEmail) {
    validateCustomerName();
    validateCustomerEmail();
    this.customerId = customerId;
    this.customerName = customerName;
    this.customerEmail = customerEmail;
  }

  public UUID getCustomerId() {
    return this.customerId;
  }

  public String getCustomerName() {
    return this.customerName;
  }

  public String getCustomerEmail() {
    return this.customerEmail;
  }

  private void validateCustomerName() {
    if (this.customerName.isBlank()) {
      throw new IllegalArgumentException("이름은 빈칸이 될 수 없습니다.");
    }
    if (this.customerName.length() > CUSTOMER_NAME_LIMIT) {
      throw new IllegalArgumentException("이름은 " + CUSTOMER_NAME_LIMIT + "자를 넘을 수 없습니다.");
    }
  }

  private void validateCustomerEmail() {
    if (this.customerEmail.isBlank()) {
      throw new RuntimeException("이메일은 빈칸이 될 수 없습니다.");
    }
    if (this.customerEmail.length() > CUSTOMER_EMAIL_LIMIT) {
      throw new IllegalArgumentException("이름은 " + CUSTOMER_EMAIL_LIMIT + "자를 넘을 수 없습니다.");
    }
  }
}
