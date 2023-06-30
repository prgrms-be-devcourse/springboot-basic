package kr.co.springbootweeklymission.infrastructure.error.exception;

import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;

public class NotSupportedException extends RuntimeException {
    public NotSupportedException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
