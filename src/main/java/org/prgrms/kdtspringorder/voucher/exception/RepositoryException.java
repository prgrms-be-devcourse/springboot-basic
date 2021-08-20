package org.prgrms.kdtspringorder.voucher.exception;

public class RepositoryException extends RuntimeException{

  public RepositoryException(String message, Throwable cause) {
    super(message, cause);
  }

  public RepositoryException(Throwable cause) {
    super(cause);
  }
}
