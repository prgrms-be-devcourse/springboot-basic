package com.prgrms.springbootbasic.common.exception;

public class AmountOutOfBoundException extends IllegalArgumentException {

    public AmountOutOfBoundException(String message) {
        super(message);
    }
}
