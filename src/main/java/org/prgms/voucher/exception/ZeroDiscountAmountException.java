package org.prgms.voucher.exception;

public class ZeroDiscountAmountException extends Exception {
    public ZeroDiscountAmountException() {
        super("[ERROR] 할인금액이 0원입니다.");
    }
}
