package org.prgrms.kdt.exception;

public class FileNotFoundCustomException extends RuntimeException {

    public FileNotFoundCustomException() {
    }

    public FileNotFoundCustomException(String message) {
        super(message);
    }
}
