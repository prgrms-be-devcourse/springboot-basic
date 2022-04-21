package com.prgms.management.common.exception;

public class InvalidParameterException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "입력값이 잘못되었습니다.";
  
  public InvalidParameterException() {
    this(DEFAULT_MESSAGE);
  }
  
  public InvalidParameterException(String message) {
    super(message);
  }
}
