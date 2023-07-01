package com.prgms.springbootbasic.global.exception;

import com.prgms.springbootbasic.global.util.ExceptionMessage;

import java.io.IOException;

public class CantWriteFileException extends RuntimeException {

    public CantWriteFileException(ExceptionMessage message) {
        super(message.getMessage());
    }

}
