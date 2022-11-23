package org.prgrms.customer;

import java.util.UUID;

public class Customer {

  private final UUID id;
  private final String name;

  public Customer(UUID id, String name) {
    this.id = id;
    this.name = name;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Customer updateName(String name) {
    return new Customer(this.getId(), name);
  }

  public String toString() {
    return "=============Customer==============" + System.lineSeparator() + "Id: " + id
        + System.lineSeparator()
        + "name: " + name + System.lineSeparator();
  }
}
