package com.prgrms.commandLineApplication.customer;

import com.prgrms.commandLineApplication.customer.validator.CustomerValidator;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

  private final UUID customerId;
  private final String email;
  private final LocalDateTime createdAt;

  private String name;
  private LocalDateTime lastLoginAt;

  protected Customer(UUID customerId, String email, String name) {
    CustomerValidator.checkId(customerId);
    CustomerValidator.checkEmail(email);
    this.customerId = customerId;
    this.email = email;
    this.createdAt = LocalDateTime.now();
    this.name = name;
    this.lastLoginAt = lastLoginAt;
  }

  public void login() {
    this.lastLoginAt = LocalDateTime.now();
  }

}
