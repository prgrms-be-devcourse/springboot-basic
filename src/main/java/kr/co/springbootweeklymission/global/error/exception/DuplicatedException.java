package kr.co.springbootweeklymission.global.error.exception;

import kr.co.springbootweeklymission.global.response.ResponseStatus;

public class DuplicatedException extends RuntimeException {
    public DuplicatedException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
