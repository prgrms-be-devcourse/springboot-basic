package org.prgms.vouchermanagement.customer.exception;

import lombok.Getter;

@Getter
public class CustomerException extends RuntimeException{

    public CustomerException(String message) {
        super(message);
    }
}
