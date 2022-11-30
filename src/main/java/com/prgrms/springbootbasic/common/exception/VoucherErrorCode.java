package com.prgrms.springbootbasic.common.exception;

import org.springframework.http.HttpStatus;

public enum VoucherErrorCode implements ErrorCode {
    AMOUNT_OUT_OF_BOUND(HttpStatus.BAD_REQUEST, "Voucher discount amount out of bound"),
    INVALID_VOUCHER_TYPE(HttpStatus.BAD_REQUEST, "Invalid voucher type");

    private final HttpStatus httpStatus;
    private final String message;

    VoucherErrorCode(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
