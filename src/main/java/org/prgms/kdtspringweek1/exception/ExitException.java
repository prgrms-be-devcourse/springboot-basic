package org.prgms.kdtspringweek1.exception;

public class ExitException extends RuntimeException {
    private String message;

    public ExitException(ExitExceptionCode exitExceptionCode) {
        this.message = exitExceptionCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
