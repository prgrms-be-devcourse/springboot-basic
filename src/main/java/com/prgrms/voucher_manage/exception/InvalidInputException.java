package com.prgrms.voucher_manage.exception;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException() {
        super("Invalid command input.");
    }
}
