package com.example.springbootbasic.controller.request;

public enum RequestCode {

    SUCCESS(200),
    FAIL(400),
    FAIL_WRONG_INPUT(401);

    private final Integer code;

    RequestCode(Integer code) {
        this.code = code;
    }

    public Integer code() {
        return code;
    }
}
