package org.prgrms.kdtspringdemo.customer.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(CustomerExceptionMessage message) {
        super(message.getMessage());
    }
}
