package com.programmers.springbootbasic.exception;

public class InvalidCustomerValueException extends IllegalArgumentException {

    public InvalidCustomerValueException(String message) {
        super(message);
    }
}
