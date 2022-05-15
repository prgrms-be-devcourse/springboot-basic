package com.mountain.voucherapp.exception;

public class JdbcUpdateNotExecuteException extends RuntimeException {
    public JdbcUpdateNotExecuteException(String message) {
        super(message);
    }
}
