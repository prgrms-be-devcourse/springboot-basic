package org.prgms.voucher.exception;

public class WrongDiscountAmountException extends Exception {
    public WrongDiscountAmountException() {
        super("[ERROR] 올바른 할인금액이 아닙니다.");
    }
}
