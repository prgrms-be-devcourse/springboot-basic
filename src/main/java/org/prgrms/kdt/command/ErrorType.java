package org.prgrms.kdt.command;

import java.text.MessageFormat;

public enum ErrorType {
  INVALID_MENU("Error: Invalid menu"),
  INVALID_INPUT("Error: Invalid input"),
  INVALID_DISCOUNT("Error: Invalid discount");

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