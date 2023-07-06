package kr.co.springbootweeklymission.common.error.exception;

import kr.co.springbootweeklymission.common.response.ResponseStatus;

public class DuplicatedException extends RuntimeException {
    public DuplicatedException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
