package org.prgrms.voucherapp.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SqlStatementFailException extends RuntimeException{
    private String message;

    @Override
    public String toString() {
        return message;
    }
}
