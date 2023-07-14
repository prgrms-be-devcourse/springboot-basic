package org.weekly.weekly.voucher.exception;

import org.weekly.weekly.util.ExceptionMsg;

public class VoucherException extends RuntimeException {
    public VoucherException(ExceptionMsg exceptionMsg) {
        super(exceptionMsg.getMsg());
    }
}
