package org.programmers.devcourse.voucher.engine.exception;

public class VoucherException extends RuntimeException {

  public VoucherException(String message) {
    super(message);
  }

  public VoucherException(Throwable cause) {
    super(cause);
  }

  public VoucherException(String message, Throwable cause) {
    super(message, cause);
  }
}
