package org.prgrms.kdt.exception;

import org.prgrms.kdt.type.ErrorType;

public class InvalidInputException extends TypedException {

  public InvalidInputException() {
    super(ErrorType.INVALID_INPUT);
  }
}