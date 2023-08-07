package org.prgrms.kdtspringdemo.customer.exception;

public class CustomerException extends RuntimeException {
    public CustomerException(CustomerExceptionMessage message) {
        super(message.getMessage());
    }
}
