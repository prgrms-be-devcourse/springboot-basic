package com.programmers.voucher.exception;

import com.programmers.voucher.constant.ErrorCode;

public class BadRequestException extends IllegalArgumentException {
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
