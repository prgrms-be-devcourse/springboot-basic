package com.programmers.voucher.exception;

import com.programmers.voucher.constant.ErrorCode;

public class NotFoundException extends IllegalArgumentException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
