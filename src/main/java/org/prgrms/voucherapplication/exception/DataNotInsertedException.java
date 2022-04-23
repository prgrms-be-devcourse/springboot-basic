package org.prgrms.voucherapplication.exception;

public class DataNotInsertedException extends RuntimeException{
    public DataNotInsertedException() {
        super(ErrorType.DATA_NOT_INSERTED.getMessage());
    }

    public DataNotInsertedException(String message) {
        super(message);
    }

    public DataNotInsertedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotInsertedException(Throwable cause) {
        super(cause);
    }

    public DataNotInsertedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
