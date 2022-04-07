package com.voucher.vouchermanagement.model.user;

import java.util.UUID;

public class User {

  private UUID id;
  private String name;

  public User(UUID id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String toString() {
    return "user id = " + id + ", name = '" + name + '\'';
  }
}
