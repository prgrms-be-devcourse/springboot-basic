package com.prgrms.voucher_manage.exception;

public class InvalidCmdInputException extends RuntimeException{
    public InvalidCmdInputException() {
        super("Invalid command input.");
    }
}
