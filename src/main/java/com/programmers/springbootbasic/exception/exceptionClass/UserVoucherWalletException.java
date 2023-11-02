package com.programmers.springbootbasic.exception.exceptionClass;

import com.programmers.springbootbasic.exception.ErrorCode;

public class UserVoucherWalletException extends RuntimeException {

    public UserVoucherWalletException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
