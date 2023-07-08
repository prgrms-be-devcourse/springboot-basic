package org.prgrms.kdt.exception;

public class FileAccessException extends RuntimeException {
    public FileAccessException() {
    }

    public FileAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
