package com.example.commandlineapplication.domain.customer;

import java.util.UUID;

public class Customer {

  private final UUID customerId;
  private String customerName;
  private String customerEmail;

  public Customer(UUID customerId, String customerName, String customerEmail) {
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

  private void checkCustomerName() {
    if (this.customerName.isBlank()) {
      throw new RuntimeException("이름은 빈칸이 될 수 없습니다.");
    }
  }
}
