package org.prgrms.kdt.exception;

import org.prgrms.kdt.type.ErrorType;

public class InvalidCustomerNameException extends TypedException{

  public InvalidCustomerNameException(ErrorType errorType) {
    super(errorType);
  }
}