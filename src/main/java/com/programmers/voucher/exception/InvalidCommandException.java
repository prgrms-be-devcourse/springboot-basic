package com.programmers.voucher.exception;

public class InvalidCommandException extends IllegalArgumentException {
    public InvalidCommandException(String message) {
        super(message);
    }
}
