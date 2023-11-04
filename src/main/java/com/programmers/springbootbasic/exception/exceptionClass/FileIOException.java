package com.programmers.springbootbasic.exception.exceptionClass;

import com.programmers.springbootbasic.exception.ErrorCode;

public class FileIOException extends CustomException {
    public FileIOException(ErrorCode errorCode) {
        super(errorCode);
    }
}
