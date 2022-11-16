package org.prgrms.exception;

public class FileNotExistException extends RuntimeException {

  public FileNotExistException(Throwable cause) {
    super("file does not exist ",cause);
  }
}
