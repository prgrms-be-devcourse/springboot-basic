package com.programmers.commandline.global.exception;

public class FileWriteException extends RuntimeException {

    public FileWriteException(String messages, Exception e) {
        super(messages, e);
    }
}
