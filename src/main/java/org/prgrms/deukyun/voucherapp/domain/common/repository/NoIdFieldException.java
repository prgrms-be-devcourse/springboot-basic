package org.prgrms.deukyun.voucherapp.domain.common.repository;

public class NoIdFieldException extends RuntimeException {

    public NoIdFieldException(String message) {
        super(message);
    }

    public NoIdFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
