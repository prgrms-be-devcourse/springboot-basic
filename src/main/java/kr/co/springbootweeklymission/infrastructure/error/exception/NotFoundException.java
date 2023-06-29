package kr.co.springbootweeklymission.infrastructure.error.exception;

import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;

public class NotFoundException extends RuntimeException {
    public NotFoundException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
