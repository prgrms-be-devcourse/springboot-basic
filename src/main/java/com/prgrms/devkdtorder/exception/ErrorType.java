package com.prgrms.devkdtorder.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum ErrorType {

    VOUCHER_NOT_FOUND("VOUCHER_NOT_FOUND", "Can not find a voucher"),
    CUSTOMER_NOT_FOUND("VOUCHER_NOT_FOUND", "Can not find a voucher");

    private final String code;
    private String message;

    ErrorType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorType with(String additionalMessage) {
        message = message + additionalMessage;
        return this;
    }

}
