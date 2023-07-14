package org.weekly.weekly.customer.exception;

import org.weekly.weekly.util.ExceptionMsg;

public class CustomerException extends RuntimeException {
    public CustomerException(ExceptionMsg exceptionMsg) {
        super(exceptionMsg.getMsg());
    }
}
