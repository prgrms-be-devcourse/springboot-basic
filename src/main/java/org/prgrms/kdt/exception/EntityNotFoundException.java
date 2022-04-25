package org.prgrms.kdt.exception;

import org.prgrms.kdt.type.ErrorType;

public class EntityNotFoundException extends TypedException {

  public EntityNotFoundException(ErrorType errorType) {
    super(errorType);
  }

  public EntityNotFoundException() {
    super(ErrorType.ENTITY_NOT_FOUND);
  }
}