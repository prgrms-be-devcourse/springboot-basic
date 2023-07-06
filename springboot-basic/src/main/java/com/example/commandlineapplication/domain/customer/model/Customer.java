package com.example.commandlineapplication.domain.customer.model;

import java.util.UUID;

public class Customer {

  private final UUID customerId;
  private String name;
  private String email;

  public Customer(UUID customerId, String name, String email) {
    this.customerId = customerId;
    this.name = name;
    this.email = email;
  }

  public UUID getCustomerId() {
    return this.customerId;
  }

  public String getName() {
    return this.name;
  }

  public String getEmail() {
    return this.email;
  }

  public String updateName(String newName) {
    this.name = newName;
    return newName;
  }

  private void checkName(String name) {
    if (name.isBlank()) {
      throw new RuntimeException("이름은 빈칸이 될 수 없습니다.");
    }
  }
}
