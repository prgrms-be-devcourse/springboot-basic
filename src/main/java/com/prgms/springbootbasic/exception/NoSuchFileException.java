package com.prgms.springbootbasic.exception;

import com.prgms.springbootbasic.util.ExceptionMessage;

public class NoSuchFileException extends RuntimeException {

    public NoSuchFileException(ExceptionMessage message) {
        super(message.getMessage());
    }

}
