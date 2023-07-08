package org.prgrms.kdt.exception;

public class NotUpdateException extends RuntimeException {
    public NotUpdateException() {
    }

    public NotUpdateException(String message) {
        super(message);
    }

    public NotUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
