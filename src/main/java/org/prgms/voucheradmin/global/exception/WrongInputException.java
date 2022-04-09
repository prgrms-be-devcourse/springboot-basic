package org.prgms.voucheradmin.global.exception;

import static org.prgms.voucheradmin.global.exception.ErrorMessage.WRONG_INPUT_EXCEPTION;

public class WrongInputException extends RuntimeException{
    public WrongInputException() {
        super(WRONG_INPUT_EXCEPTION.getMessage());
    }

    public WrongInputException(String message) {
        super(message);
    }

    public WrongInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongInputException(Throwable cause) {
        super(cause);
    }

    public WrongInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
