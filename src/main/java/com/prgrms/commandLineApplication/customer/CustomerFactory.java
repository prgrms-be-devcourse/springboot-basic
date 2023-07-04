package com.prgrms.commandLineApplication.customer;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerFactory {
  public static Customer of(String email, String name) {
    return new Customer(UUID.randomUUID(), email, name);
  }

}
