package com.programmers.springbootbasic.exception.exceptionClass;

import com.programmers.springbootbasic.exception.ErrorCode;

public class FileIOException extends RuntimeException {

    public FileIOException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
