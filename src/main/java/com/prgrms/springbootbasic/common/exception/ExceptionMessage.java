package com.prgrms.springbootbasic.common.exception;

public class ExceptionMessage {

  private ExceptionMessage(){}

  public static final String ILLEGAL_STATE_EXCEPTION_WHEN_DISCOUNT = "Discount result is under zero. You cannot use this voucher.";
  public static final String VOUCHER_NOT_SUPPORTED = "Voucher not supported yet.";
}
