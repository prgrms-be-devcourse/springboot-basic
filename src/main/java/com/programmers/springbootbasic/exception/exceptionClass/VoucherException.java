package com.programmers.springbootbasic.exception.exceptionClass;

import com.programmers.springbootbasic.exception.ErrorCode;

public class VoucherException extends RuntimeException {

    public VoucherException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
