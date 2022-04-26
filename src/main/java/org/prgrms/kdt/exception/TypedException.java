package org.prgrms.kdt.exception;

import org.prgrms.kdt.type.ErrorType;

public class TypedException extends RuntimeException {

  public TypedException(ErrorType errorType) {
    super(errorType.getErrorMessage());
  }
}