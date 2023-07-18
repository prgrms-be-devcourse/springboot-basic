package com.devcourse.voucherapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus status;
    private final String causeInput;

    protected BusinessException(ExceptionRule rule, String causeInput) {
        super(rule.getMessage());
        this.status = rule.getStatus();
        this.causeInput = causeInput;
    }
}
