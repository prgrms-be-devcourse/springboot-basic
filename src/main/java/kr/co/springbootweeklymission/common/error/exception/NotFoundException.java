package kr.co.springbootweeklymission.common.error.exception;

import kr.co.springbootweeklymission.common.response.ResponseStatus;

public class NotFoundException extends RuntimeException {
    public NotFoundException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
