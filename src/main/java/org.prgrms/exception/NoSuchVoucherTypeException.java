package org.prgrms.exception;

public class NoSuchVoucherTypeException extends RuntimeException {

  public NoSuchVoucherTypeException(String voucherType) {
    super(voucherType + "은 존재하지 않습니다. 다시입력해주세요");
  }
}
