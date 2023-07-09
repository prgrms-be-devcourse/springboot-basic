package com.programmers.exception;

public class InvalidVoucherValueException extends IllegalArgumentException {

    public InvalidVoucherValueException(String message) {
        super(message);
    }
}
