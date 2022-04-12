package com.prgms.management.voucher.exception;

public class InvalidVoucherParameterException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "유효한 값이 입력되지 않았습니다.";

    public InvalidVoucherParameterException(String message) {
        super(message);
    }

    public InvalidVoucherParameterException() {
        this(DEFAULT_MESSAGE);
    }
}
