package org.prgms.voucherProgram.exception;

public class WrongDiscountPercentException extends Exception {
    public WrongDiscountPercentException() {
        super("[ERROR] 올바른 할인퍼센트가 아닙니다.");
    }
}
