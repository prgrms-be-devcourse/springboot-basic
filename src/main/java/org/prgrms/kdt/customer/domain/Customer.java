package org.prgrms.kdt.customer.domain;

import java.text.MessageFormat;
import java.util.UUID;

public class Customer {
  private final UUID customerId;
  private final String name;

  public Customer(UUID customerId, String name) {
    this.customerId = customerId;
    this.name = name;
  }

  @Override
  public String toString() {
    return MessageFormat.format("Customer [customerId={0}, name={1}]", customerId, name);
  }
}