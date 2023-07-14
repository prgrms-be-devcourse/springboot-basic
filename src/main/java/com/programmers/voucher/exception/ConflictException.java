package com.programmers.voucher.exception;

import com.programmers.voucher.constant.ErrorCode;

public class ConflictException extends IllegalArgumentException {
    public ConflictException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
