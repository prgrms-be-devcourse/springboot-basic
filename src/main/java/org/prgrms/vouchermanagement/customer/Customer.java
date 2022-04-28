package org.prgrms.vouchermanagement.customer;

import org.prgrms.vouchermanagement.customer.vo.EmailVO;
import org.prgrms.vouchermanagement.customer.vo.NameVO;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Customer {
  private final UUID customerId;
  private NameVO name;
  private final EmailVO email;
  private LocalDateTime lastLoginAt;
  private final LocalDateTime createdAt;
  private List<Voucher> wallet;

  public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
    validateName(name);
    this.customerId = customerId;
    this.name = new NameVO(name);
    this.email = new EmailVO(email);
    this.createdAt = createdAt;
    wallet = new ArrayList<>();
  }

  public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
    this.customerId = customerId;
    this.name = new NameVO(name);
    this.email = new EmailVO(email);
    this.lastLoginAt = lastLoginAt;
    this.createdAt = createdAt;
    wallet = new ArrayList<>();
  }

  public List<Voucher> getWallet() {
    return wallet;
  }


  private void validateName(String name) {
    if (name.isBlank()) {
      throw new RuntimeException("Name should not be black");
    }
  }

  public void login() {
    this.lastLoginAt = LocalDateTime.now();
  }

  public UUID getCustomerId() {
    return customerId;
  }

  public String getName() {
    return name.getName();
  }

  public EmailVO getEmail() {
    return email;
  }

  public LocalDateTime getLastLoginAt() {
    return lastLoginAt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Customer customer = (Customer) o;

    return customerId.equals(customer.customerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerId);
  }

  @Override
  public String toString() {
    return "Customer{" +
      "customerId=" + customerId +
      ", name='" + name.getName() + '\'' +
      ", email='" + email.getAddress() + '\'' +
      ", lastLoginAt=" + lastLoginAt +
      ", createdAt=" + createdAt +
      '}';
  }

  public void changeName(String newName) {
    name.changeName(newName);
  }
}
