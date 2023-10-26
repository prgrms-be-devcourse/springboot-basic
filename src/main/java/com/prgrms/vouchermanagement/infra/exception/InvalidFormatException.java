package com.prgrms.vouchermanagement.infra.exception;

public class InvalidFormatException extends IllegalArgumentException {

    public InvalidFormatException(String message) {
        super(message);
    }
}
