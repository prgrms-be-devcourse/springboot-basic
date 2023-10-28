package org.prgms.kdtspringweek1.exception;

public class JdbcException extends RuntimeException {
    private String message;

    public JdbcException(JdbcExceptionCode jdbcExceptionCode) {
        this.message = jdbcExceptionCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
