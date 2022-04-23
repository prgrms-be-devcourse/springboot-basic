package org.prgrms.kdt.domain;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;
import org.prgrms.kdt.exception.InvalidCustomerNameException;
import org.prgrms.kdt.exception.InvalidEmailException;
import org.prgrms.kdt.type.ErrorType;
import org.springframework.util.StringUtils;

public class Customer {

  private static final int MAX_NAME_LENGTH = 20;
  private static final int MAX_EMAIL_LENGTH = 50;
  private static final Pattern CUSTOMER_NAME_PATTERN =
      Pattern.compile("^[a-zA-Z]+$");
  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[a-zA-Z\\d_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z\\d.-]+$");

  private final UUID customerId;
  private final String name;
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
    validate();
  }

  public Customer(String name, String email) {
    this(UUID.randomUUID(), name, email, LocalDateTime.now(), LocalDateTime.now());
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

  private void validate() {
    validateName();
    validateEmail();
  }

  private void validateEmail() {
    if (!StringUtils.hasText(email)) {
      throw new InvalidEmailException(ErrorType.EMPTY_EMAIL);
    }
    if (email.length() > MAX_EMAIL_LENGTH) {
      throw new InvalidEmailException(ErrorType.INVALID_EMAIL_LENGTH);
    }
    if (!isValidEmail(email)) {
      throw new InvalidEmailException(ErrorType.INVALID_EMAIL_FORMAT);
    }
  }

  private void validateName() {
    if (!StringUtils.hasText(name)) {
      throw new InvalidCustomerNameException(ErrorType.EMPTY_CUSTOMER_NAME);
    }
    if (name.length() > MAX_NAME_LENGTH) {
      throw new InvalidCustomerNameException(ErrorType.INVALID_CUSTOMER_LENGTH);
    }
    if (!isValidName(name)) {
      throw new InvalidCustomerNameException(ErrorType.INVALID_CUSTOMER_NAME_FORMAT);
    }
  }

  private boolean isValidEmail(String email) {
    return EMAIL_PATTERN.matcher(email).matches();
  }

  private boolean isValidName(String name) {
    return CUSTOMER_NAME_PATTERN.matcher(name).matches();
  }
}