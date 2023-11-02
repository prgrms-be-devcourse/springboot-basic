package com.programmers.springbootbasic.exception.exceptionClass;

import com.programmers.springbootbasic.exception.ErrorCode;

public class UserException extends RuntimeException {

    public UserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
