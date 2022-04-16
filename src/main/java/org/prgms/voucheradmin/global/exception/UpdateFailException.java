package org.prgms.voucheradmin.global.exception;

public class UpdateFailException extends RuntimeException{
    private static final String UPDATE_FAIL_EXCEPTION = "update fail";


    public UpdateFailException() {
        super(UPDATE_FAIL_EXCEPTION);
    }

    public UpdateFailException(String message) {
        super(message);
    }

    public UpdateFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateFailException(Throwable cause) {
        super(cause);
    }

    public UpdateFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
