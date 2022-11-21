package org.prgrms.kdt.exceptions;

public class InvalidParameterException extends RuntimeException{
    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
