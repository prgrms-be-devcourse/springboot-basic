package com.programmers.springmission.global.exception;

public class VoucherException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public VoucherException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}

