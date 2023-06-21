package com.programmers.voucher.exception;

public class VoucherException extends RuntimeException{

    private VoucherErrorCode voucherErrorCode;

    public VoucherException(VoucherErrorCode voucherErrorCode) {
        super(voucherErrorCode.getErrorMessage());
        this.voucherErrorCode = voucherErrorCode;
    }
}
