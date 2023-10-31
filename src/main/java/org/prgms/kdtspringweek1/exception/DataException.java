package org.prgms.kdtspringweek1.exception;

public class DataException extends RuntimeException {
    private String message;

    public DataException(DataExceptionCode dataExceptionCode) {
        this.message = dataExceptionCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
