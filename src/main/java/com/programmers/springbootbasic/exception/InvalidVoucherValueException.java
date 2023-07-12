package com.programmers.springbootbasic.exception;

public class InvalidVoucherValueException extends IllegalArgumentException {

    public InvalidVoucherValueException(String message) {
        super(message);
    }
}
