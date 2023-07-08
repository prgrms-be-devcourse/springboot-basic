package org.promgrammers.springbootbasic.global.error.exception.repository;

public class FileWriteException extends RuntimeException {

    public FileWriteException(String message) {
        super(message);
    }
}
