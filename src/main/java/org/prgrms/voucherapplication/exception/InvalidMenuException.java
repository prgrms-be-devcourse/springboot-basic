package org.prgrms.voucherapplication.exception;

/**
 * 잘못된 메뉴 입력 시 발생하는 Exception
 */
public class InvalidMenuException extends RuntimeException {
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
