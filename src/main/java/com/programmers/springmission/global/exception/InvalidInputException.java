package com.programmers.springmission.global.exception;

public class VoucherException extends RuntimeException {

    public VoucherException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}

