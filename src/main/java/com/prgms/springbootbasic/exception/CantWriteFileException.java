package com.prgms.springbootbasic.exception;

import com.prgms.springbootbasic.util.ExceptionMessage;

public class CantWriteFileException extends RuntimeException {

    public CantWriteFileException(ExceptionMessage message) {
        super(message.getMessage());
    }

}
