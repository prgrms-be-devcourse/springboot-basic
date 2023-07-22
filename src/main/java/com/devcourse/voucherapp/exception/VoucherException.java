package com.devcourse.voucherapp.exception;

public class VoucherException extends BusinessException {

    public VoucherException(ExceptionRule rule, String causeInput) {
        super(rule, causeInput);
    }
}
