package org.prgrms.kdt.domain;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.prgrms.kdt.dto.CustomerDto;

public class Customer {

  private final UUID customerId;
  @NotBlank
  @Size(min = 2, max = 20)
  @Pattern(regexp = "[a-zA-Z]")
  private final String name;
  @Email
  @NotBlank
  @Size(max = 50)
  private final String email;
  private final LocalDateTime createdAt;
  private final LocalDateTime lastLoginAt;

  public Customer(
      UUID customerId,
      String name,
      String email,
      LocalDateTime createdAt,
      LocalDateTime lastLoginAt) {
    this.customerId = customerId;
    this.name = name;
    this.email = email;
    this.createdAt = createdAt;
    this.lastLoginAt = lastLoginAt;
  }

  public Customer(String name, String email) {
    this(UUID.randomUUID(), name, email, LocalDateTime.now(), LocalDateTime.now());
  }

  public Customer(CustomerDto customerDto) {
    this(UUID.randomUUID(),
        customerDto.name(),
        customerDto.email(),
        LocalDateTime.now(),
        LocalDateTime.now());
  }

  public UUID getCustomerId() {
    return customerId;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getLastLoginAt() {
    return lastLoginAt;
  }

  @Override
  public String toString() {
    return MessageFormat.format("Customer [customerId={0}, name={1}, email={2}]",
        customerId, name, email);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Customer customer = (Customer) o;
    return customerId.equals(customer.customerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerId);
  }
}