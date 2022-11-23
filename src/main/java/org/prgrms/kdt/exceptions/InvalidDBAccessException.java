package org.prgrms.kdt.exceptions;

public class InvalidDBAccessException extends RuntimeException{
    public InvalidDBAccessException(String message) {
        super(message);
    }

    public InvalidDBAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
