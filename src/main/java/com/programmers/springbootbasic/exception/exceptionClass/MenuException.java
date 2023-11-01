package com.programmers.springbootbasic.exception.exceptionClass;

import com.programmers.springbootbasic.exception.ErrorCode;

public class MenuException extends CustomException {
    public MenuException(ErrorCode errorCode) {
        super(errorCode);
    }
}
