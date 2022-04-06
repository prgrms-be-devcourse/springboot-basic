package org.programmers.devcourse.voucher.engine.exception;

import java.text.MessageFormat;

public class VoucherException extends Exception {


  public VoucherException(String message) {
    super(MessageFormat.format("[ERROR] : {0}", message));
  }
}
