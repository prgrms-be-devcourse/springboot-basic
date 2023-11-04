package com.programmers.springbootbasic.exception.exceptionClass;

import com.programmers.springbootbasic.exception.ErrorCode;

public class SystemException extends CustomException {

    public SystemException(ErrorCode errorCode) {
        super(errorCode);
    }
}
