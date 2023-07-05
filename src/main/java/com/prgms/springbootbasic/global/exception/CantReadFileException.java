package com.prgms.springbootbasic.global.exception;

import com.prgms.springbootbasic.global.util.ExceptionMessage;

import java.io.IOException;

public class CantReadFileException extends RuntimeException {

    public CantReadFileException(ExceptionMessage message) {
        super(message.getMessage());
    }

}
