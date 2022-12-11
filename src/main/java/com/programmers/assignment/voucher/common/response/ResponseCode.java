package com.programmers.assignment.voucher.common.response;

import org.springframework.http.HttpStatus;

public enum ResponseCode {
    SUCCESS(HttpStatus.OK.value()),

    NOT_FOUND_CUSTOMER(HttpStatus.NOT_FOUND.value()),
    NOT_FOUND_VOUCHER(HttpStatus.NOT_FOUND.value()),
    INVALID_DISCOUNT_VALUE(HttpStatus.BAD_REQUEST.value()),
    NOT_AVAILABLE_VOUCHER(HttpStatus.BAD_REQUEST.value());

    private int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
