package com.prgms.springbootbasic.exception;

import com.prgms.springbootbasic.util.ExceptionMessage;

import java.io.FileNotFoundException;

public class NoSuchFileException extends FileNotFoundException {

    public NoSuchFileException(ExceptionMessage message) {
        super(message.getMessage());
    }

}
