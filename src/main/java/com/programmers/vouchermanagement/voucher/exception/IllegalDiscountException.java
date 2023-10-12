package com.programmers.vouchermanagement.voucher.exception;

public class IllegalDiscountException extends RuntimeException {

    public IllegalDiscountException(String errorMessage) {
        super(errorMessage);
    }
}
