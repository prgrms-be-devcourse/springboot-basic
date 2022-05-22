package org.prgms.voucheradmin.global.exception.customexception;

import java.util.UUID;

public class CustomerNotFoundException extends RuntimeException{
    private static final String CUSTOMER_NOT_FOUND_EXCEPTION = "can't find a customer";


    public CustomerNotFoundException(UUID customerId) {
        super(CUSTOMER_NOT_FOUND_EXCEPTION+" "+customerId);
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerNotFoundException(Throwable cause) {
        super(cause);
    }

    public CustomerNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
