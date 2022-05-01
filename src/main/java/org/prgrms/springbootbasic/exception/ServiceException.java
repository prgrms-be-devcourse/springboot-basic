package org.prgrms.springbootbasic.exception;

public abstract class ServiceException extends RuntimeException {

    public ServiceException() {
        super();
    }

    @Override
    public abstract String getMessage();
}
