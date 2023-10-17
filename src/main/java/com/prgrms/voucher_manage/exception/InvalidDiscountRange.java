package com.prgrms.voucher_manage.exception;

public class InvalidDiscountRange extends RuntimeException{
    public InvalidDiscountRange() {
        super("Invalid discount range.");
    }
}
