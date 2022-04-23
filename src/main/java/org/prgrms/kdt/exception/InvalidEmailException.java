package org.prgrms.kdt.exception;

import org.prgrms.kdt.type.ErrorType;

public class InvalidEmailException extends TypedException{

  public InvalidEmailException(ErrorType errorType) {
    super(errorType);
  }
}