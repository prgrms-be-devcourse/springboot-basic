package kr.co.springbootweeklymission.common.error.exception;

import kr.co.springbootweeklymission.common.response.ResponseStatus;

public class NotSupportedException extends RuntimeException {
    public NotSupportedException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
    }
}
