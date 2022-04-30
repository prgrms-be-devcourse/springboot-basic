package org.prgrms.kdt.domain.common.exception;

public class NotUpdatedException extends RuntimeException{

    public NotUpdatedException(ExceptionType errorType) {
        super(errorType.getMsg());
    }
}
