package kr.co.springbootweeklymission.infrastructure.error.exception;

import kr.co.springbootweeklymission.infrastructure.error.model.ResponseStatus;

public class DuplicatedException extends RuntimeException {
    public DuplicatedException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
