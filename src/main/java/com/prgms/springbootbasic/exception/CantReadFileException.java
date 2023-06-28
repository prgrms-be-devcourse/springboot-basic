package com.prgms.springbootbasic.exception;

import com.prgms.springbootbasic.util.ExceptionMessage;

import java.io.IOException;

public class CantReadFileException extends IOException {

    public CantReadFileException(ExceptionMessage message) {
        super(message.getMessage());
    }

}
