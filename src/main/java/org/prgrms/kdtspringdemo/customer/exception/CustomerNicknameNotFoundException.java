package org.prgrms.kdtspringdemo.customer.exception;

public class CustomerNicknameNotFoundException extends RuntimeException{
    public CustomerNicknameNotFoundException(CustomerExceptionMessage message) {
        super(message.getMessage());
    }
}
