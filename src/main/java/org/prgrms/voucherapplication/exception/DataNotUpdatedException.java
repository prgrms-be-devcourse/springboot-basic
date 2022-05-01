package org.prgrms.voucherapplication.exception;

public class DataNotUpdatedException extends RuntimeException{
    public DataNotUpdatedException() {
        super(ErrorType.DATA_NOT_UPDATED.getMessage());
    }

    public DataNotUpdatedException(String message) {
        super(message);
    }

    public DataNotUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotUpdatedException(Throwable cause) {
        super(cause);
    }

    public DataNotUpdatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
