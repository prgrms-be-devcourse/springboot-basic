package org.prgrms.exception;

public class PaymentCannotBeNegativeException extends RuntimeException {

  public PaymentCannotBeNegativeException(Long payment) {
    super("The payment amount must be at least 0 won."
        + System.lineSeparator()
        + "* Current payment amount :" + payment + "won");

  }

}
