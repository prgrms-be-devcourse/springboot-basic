package org.prgrms.voucherapplication.exception;

public class InvalidMenuException extends RuntimeException{
    public InvalidMenuException() {
        super(ErrorType.INVALID_MENU.getMessage());
    }

    public InvalidMenuException(String message) {
        super();
    }

    public InvalidMenuException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMenuException(Throwable cause) {
        super(cause);
    }

    public InvalidMenuException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
