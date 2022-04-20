package com.prgrms.voucher_manager.exception;

public class WrongVoucherValueException extends RuntimeException{
    public WrongVoucherValueException(String message) {
        super(message);
    }
}
