package com.programmers.springbasic.domain.customer.exception;

import com.programmers.springbasic.global.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerException extends RuntimeException {
    private final ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
