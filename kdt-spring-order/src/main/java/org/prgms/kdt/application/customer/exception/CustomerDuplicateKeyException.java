package org.prgms.kdt.application.customer.exception;

import org.springframework.dao.DuplicateKeyException;

public class CustomerDuplicateKeyException extends RuntimeException {

    public CustomerDuplicateKeyException() {
    }

    public CustomerDuplicateKeyException(String message) {
        super(message);
    }

    public CustomerDuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerDuplicateKeyException(Throwable cause) {
        super(cause);
    }

}
