package org.programmers.springbootbasic.voucher.domain;

public class IllegalVoucherTypeException extends IllegalArgumentException {
    public IllegalVoucherTypeException() {
    }

    public IllegalVoucherTypeException(String s) {
        super(s);
    }

    public IllegalVoucherTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalVoucherTypeException(Throwable cause) {
        super(cause);
    }
}