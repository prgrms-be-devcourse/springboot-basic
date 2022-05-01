package org.prgrms.voucherapp.exception;

public class SqlStatementFailException extends RuntimeException {
    public SqlStatementFailException(String message) {
        super(message);
    }
}
