package com.pppp0722.vouchermanagement.exception;

public class InvalidAmountException extends RuntimeException {

    public InvalidAmountException() {
    }

    public InvalidAmountException(String message) {
        super(message);
    }
}