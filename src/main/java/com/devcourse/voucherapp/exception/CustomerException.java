package com.devcourse.voucherapp.exception;

public class CustomerException extends BusinessException {

    public CustomerException(ExceptionRule rule, String causeInput) {
        super(rule, causeInput);
    }
}
