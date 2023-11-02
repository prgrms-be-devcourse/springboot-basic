package com.programmers.springbootbasic.exception.exceptionClass;

import com.programmers.springbootbasic.exception.ErrorCode;

public class SystemException extends RuntimeException {

    private final ErrorCode errorCode;

    public SystemException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
