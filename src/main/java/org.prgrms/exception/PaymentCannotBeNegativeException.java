package org.prgrms.exception;

public class PaymentCannotBeNegativeException extends RuntimeException {

  public PaymentCannotBeNegativeException(Long payment) {
    super("결제금액은 0원 이상이어야 합니다. *현재 결제금액 :" + payment + "원");
  }

}
