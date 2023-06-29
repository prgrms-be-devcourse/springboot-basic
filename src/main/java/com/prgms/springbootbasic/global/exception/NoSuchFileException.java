package com.prgms.springbootbasic.global.exception;

import com.prgms.springbootbasic.global.util.ExceptionMessage;

import java.io.FileNotFoundException;

public class NoSuchFileException extends FileNotFoundException {

    public NoSuchFileException(ExceptionMessage message) {
        super(message.getMessage());
    }

}
