package org.prgrms.exception;

public class NoSuchVoucherTypeException extends RuntimeException {

  public NoSuchVoucherTypeException(String voucherType) {
    super("' " + voucherType + " ' does not exist. Please re-enter");
  }
}
