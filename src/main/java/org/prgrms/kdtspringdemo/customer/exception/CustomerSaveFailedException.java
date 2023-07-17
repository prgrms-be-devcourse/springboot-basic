package org.prgrms.kdtspringdemo.customer.exception;

public class CustomerSaveFailedException extends RuntimeException {
    public CustomerSaveFailedException(CustomerExceptionMessage message) {
        super(message.getMessage());
    }
}
