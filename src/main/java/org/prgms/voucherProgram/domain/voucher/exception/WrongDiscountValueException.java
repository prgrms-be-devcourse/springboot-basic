package org.prgms.voucherProgram.domain.voucher.exception;

public class WrongDiscountValueException extends IllegalArgumentException {
    private static final String MESSAGE = "[ERROR] 올바른 할인값이 아닙니다.";

    public WrongDiscountValueException() {
        super(MESSAGE);
    }

    public WrongDiscountValueException(String message) {
        super(message);
    }
}
