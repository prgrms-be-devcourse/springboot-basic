package com.prgms.springbootbasic.global.exception;

import com.prgms.springbootbasic.global.util.ExceptionMessage;

public class NoSuchFileException extends RuntimeException {

    public NoSuchFileException(ExceptionMessage message) {
        super(message.getMessage());
    }

}
