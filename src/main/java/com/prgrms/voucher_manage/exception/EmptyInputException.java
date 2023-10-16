package com.prgrms.voucher_manage.exception;

public class EmptyInputException extends RuntimeException {
    public EmptyInputException() {
        super("Empty input is not allowed.");
    }
}
