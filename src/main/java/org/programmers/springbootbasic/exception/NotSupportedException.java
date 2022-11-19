package org.programmers.springbootbasic.exception;

public class NotSupportedException extends RuntimeException{
    public NotSupportedException() {
    }

    public NotSupportedException(String message) {
        super(message);
    }

    public NotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}
