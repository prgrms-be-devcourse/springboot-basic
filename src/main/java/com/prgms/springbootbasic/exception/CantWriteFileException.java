package com.prgms.springbootbasic.exception;

import com.prgms.springbootbasic.util.ExceptionMessage;

import java.io.IOException;

public class CantWriteFileException extends IOException {

    public CantWriteFileException(ExceptionMessage message) {
        super(message.getMessage());
    }

}
