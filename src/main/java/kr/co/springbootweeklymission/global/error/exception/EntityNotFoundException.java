package kr.co.springbootweeklymission.global.error.exception;

import kr.co.springbootweeklymission.global.error.model.ResponseStatus;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
