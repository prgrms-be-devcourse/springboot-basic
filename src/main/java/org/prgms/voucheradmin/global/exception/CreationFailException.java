package org.prgms.voucheradmin.global.exception;

public class CreationFailException extends RuntimeException{
    private static final String CREATION_FAIL_EXCEPTION = "create fail";

    public CreationFailException() {
        super(CREATION_FAIL_EXCEPTION);
    }

    public CreationFailException(String message) {
        super(message);
    }

    public CreationFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreationFailException(Throwable cause) {
        super(cause);
    }

    public CreationFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
