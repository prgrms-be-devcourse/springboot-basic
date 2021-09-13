package com.prgrms.devkdtorder.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum ErrorType {

    VOUCHER_NOT_FOUND("VOUCHER_NOT_FOUND", "Can not find a voucher"),
    CUSTOMER_NOT_FOUND("CUSTOMER_NOT_FOUND", "Can not find a customer");

    private final String code;
    private final String message;

    ErrorType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
