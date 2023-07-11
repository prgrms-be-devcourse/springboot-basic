package com.prgrms.commandLineApplication.customer;

import com.prgrms.commandLineApplication.customer.validator.CustomerValidator;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

  private final UUID customerId;
  private final String email;
  private final LocalDateTime createdAt;
  private String customerName;

  private Customer(UUID customerId, String customerName, String email) {
    this.customerId = customerId;
    this.customerName = customerName;
    this.email = email;
    this.createdAt = LocalDateTime.now();
  }

  public static Customer of(UUID customerId, String customerName, String email) {
    CustomerValidator.checkId(customerId);
    CustomerValidator.checkName(customerName);
    CustomerValidator.checkEmail(email);
    return new Customer(customerId, email, name);
  }

}
