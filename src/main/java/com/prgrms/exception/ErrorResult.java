package com.prgrms.exception;

import lombok.Getter;

@Getter
public class ErrorResult {

    private String code;
    private String message;

    public ErrorResult(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
