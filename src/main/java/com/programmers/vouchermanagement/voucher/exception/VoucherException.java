package com.programmers.vouchermanagement.voucher.exception;

import com.programmers.vouchermanagement.global.exception.BaseException;
import com.programmers.vouchermanagement.global.exception.ErrorCode;

public class VoucherException extends BaseException {
    public VoucherException(ErrorCode errorCode) {
        super(errorCode);
    }
}
