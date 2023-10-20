package com.programmers.springbootbasic.exception.exceptionClass;

import com.programmers.springbootbasic.exception.ErrorCode;

public class CustomException extends RuntimeException {

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
