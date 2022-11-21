package org.prgrms.kdt.customer.exception;

public class NotPresentInRepositoryException extends RuntimeException {

    public NotPresentInRepositoryException(String message) {
        super(message);
    }

    public NotPresentInRepositoryException(String message, Exception e) {
        super(message, e);
    }
}
