package com.programmers.vouchermanagement.customer.exception;

import com.programmers.vouchermanagement.global.exception.BaseException;
import com.programmers.vouchermanagement.global.exception.ErrorCode;

public class CustomerException extends BaseException {
    public CustomerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
