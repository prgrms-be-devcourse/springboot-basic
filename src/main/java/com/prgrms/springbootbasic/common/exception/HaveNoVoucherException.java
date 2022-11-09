package com.prgrms.springbootbasic.common.exception;

public class HaveNoVoucherException extends RuntimeException{
  private static final String MESSAGE = "You don't have any voucher";
  public HaveNoVoucherException(){
    super(MESSAGE);
  }
}
