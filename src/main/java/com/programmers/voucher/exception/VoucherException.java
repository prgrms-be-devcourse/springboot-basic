package com.programmers.voucher.exception;

public class VoucherException extends RuntimeException{

    public VoucherException(VoucherErrorCode voucherErrorCode) {
        super(voucherErrorCode.getErrorMessage());
    }
}
