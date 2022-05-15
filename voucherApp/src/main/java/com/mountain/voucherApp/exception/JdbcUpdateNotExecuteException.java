package com.mountain.voucherApp.exception;

public class JdbcUpdateNotExecuteException extends RuntimeException {
    public JdbcUpdateNotExecuteException(String message) {
        super(message);
    }
}
