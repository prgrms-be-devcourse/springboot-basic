package com.prgrms.voucher_manage.exception;

public class InvalidDiscountRangeException extends RuntimeException {
    public InvalidDiscountRangeException() {
        super("Invalid discount range.");
    }
}
