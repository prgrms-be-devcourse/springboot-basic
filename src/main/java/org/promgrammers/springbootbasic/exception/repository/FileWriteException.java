package org.promgrammers.springbootbasic.exception.repository;

public class FileWriteException extends RuntimeException {

    public FileWriteException(String message) {
        super(message);
    }
}
