package com.prgrms.springbootbasic.common.exception;

public class InvalidVoucherTypeException extends RuntimeException {

    public InvalidVoucherTypeException(String message) {
        super(message);
    }
}
