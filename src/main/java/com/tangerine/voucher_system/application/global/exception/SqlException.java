package com.tangerine.voucher_system.application.global.exception;

public class SqlException extends RuntimeException {
    public SqlException() {
    }

    public SqlException(Exception e) {
        super(e);
    }

    public SqlException(String message) {
        super(message);
    }

    public SqlException(String message, Exception exception) {
        super(message, exception);
    }
}
