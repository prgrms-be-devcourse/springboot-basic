package com.programmers.springbootbasic.exception.exceptionClass;

import com.programmers.springbootbasic.exception.ErrorCode;

public class SystemException extends RuntimeException{

        public SystemException(ErrorCode errorCode) {
            super(errorCode.getMessage());
        }
}
