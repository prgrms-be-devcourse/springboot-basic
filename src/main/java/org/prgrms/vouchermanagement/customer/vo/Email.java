package org.prgrms.vouchermanagement.customer.vo;

import org.springframework.util.Assert;

import java.util.Objects;

public class Email {
  private final String address;

  public Email(String address) {
    Assert.notNull(address, "address should not be null");
    Assert.isTrue(4 <= address.length() && address.length() <= 50, "address length must be between 4 and 50 characters.");
    Assert.isTrue(checkAddress(address), "Invalid email address");
    this.address = address;
  }

  private static boolean checkAddress(String address) {
    return address.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Email email = (Email) o;
    return Objects.equals(address, email.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address);
  }

  @Override
  public String toString() {
    return address;
  }

  public String getAddress() {
    return address;
  }
}
