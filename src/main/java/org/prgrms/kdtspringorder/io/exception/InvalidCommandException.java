package org.prgrms.kdtspringorder.io.exception;

import java.io.IOException;

// Command 클래스에 대해서 Validation을 수행 하는 과정에서 발생하는 예외 들
public class InvalidCommandException extends IOException {
  public InvalidCommandException(String message) {
    super(message);
  }
}
