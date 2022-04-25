package org.prgrms.kdt.type;

import java.text.MessageFormat;

public enum ErrorType {
  INVALID_MENU("Error: Invalid menu"),
  INVALID_INPUT("Error: Invalid input"),
  INVALID_DISCOUNT("Error: Invalid discount"),

  INVALID_EMAIL_FORMAT("Error: Email format is invalid"),
  INVALID_EMAIL_LENGTH("Error: Email cannot be longer than 50 characters"),
  EMPTY_EMAIL("Error: Email cannot be blank"),

  INVALID_CUSTOMER_NAME_FORMAT("Error: Customer name only accepts alphabets"),
  INVALID_CUSTOMER_LENGTH("Error: Customer name cannot be longer than 20 characters"),
  EMPTY_CUSTOMER_NAME("Error: Customer name cannot be blank"),

  DUPLICATED_EMAIL("Error: Email already exists"),
  ENTITY_NOT_FOUND("Error: Can't find entity");

  private final String errorMessage;

  ErrorType(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public String getErrorMessage(String message) {
    return MessageFormat.format("{0} - {1}", errorMessage, message);
  }
}