package kr.co.springbootweeklymission.global.error.exception;

import kr.co.springbootweeklymission.global.response.ResponseStatus;

public class NotSupportedException extends RuntimeException {
    public NotSupportedException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
