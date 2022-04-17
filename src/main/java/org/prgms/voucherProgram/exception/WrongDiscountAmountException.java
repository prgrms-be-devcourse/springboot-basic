package org.prgms.voucherProgram.exception;

public class WrongDiscountAmountException extends IllegalArgumentException {
    private static final String MESSAGE = "[ERROR] 올바른 할인금액이 아닙니다.";

    public WrongDiscountAmountException() {
        super(MESSAGE);
    }

    public WrongDiscountAmountException(String message) {
        super(message);
    }
}
