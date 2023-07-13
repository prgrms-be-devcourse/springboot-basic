package com.prgms.springbootbasic.exception;

import com.prgms.springbootbasic.util.ExceptionMessage;

public class CantReadFileException extends RuntimeException {

    public CantReadFileException(ExceptionMessage message) {
        super(message.getMessage());
    }

}
