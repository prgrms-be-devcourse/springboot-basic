package org.prgms.voucherProgram.exception;

public class WrongDiscountPercentException extends IllegalArgumentException {
    private static final String MESSAGE = "[ERROR] 올바른 할인퍼센트가 아닙니다.";

    public WrongDiscountPercentException() {
        super(MESSAGE);
    }

    public WrongDiscountPercentException(String message) {
        super(message);
    }
}
