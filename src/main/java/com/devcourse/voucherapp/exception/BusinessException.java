package com.devcourse.voucherapp.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ExceptionRule rule;
    private final String causeInput;

    protected BusinessException(ExceptionRule rule, String causeInput) {
        super(rule.getMessage());
        this.rule = rule;
        this.causeInput = causeInput;
    }
}
