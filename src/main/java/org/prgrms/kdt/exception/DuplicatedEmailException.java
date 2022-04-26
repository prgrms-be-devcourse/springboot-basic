package org.prgrms.kdt.exception;

import org.prgrms.kdt.type.ErrorType;

public class DuplicatedEmailException extends TypedException {

  public DuplicatedEmailException() {
    super(ErrorType.DUPLICATED_EMAIL);
  }
}