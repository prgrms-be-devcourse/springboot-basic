package org.prgrms.kdt.customer.exception;

public class CustomerNotUpdatedException extends RuntimeException{
    public CustomerNotUpdatedException(String errorMessage) {
        super(errorMessage);
    }
}
