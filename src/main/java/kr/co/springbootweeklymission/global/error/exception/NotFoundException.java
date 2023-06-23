package kr.co.springbootweeklymission.global.error.exception;

import kr.co.springbootweeklymission.global.error.model.ResponseStatus;

public class NotFoundException extends RuntimeException {
    public NotFoundException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
