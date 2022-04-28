package org.programmers.devcourse.voucher.engine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
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
