package com.example.springbootbasic.controller.response;

public enum ResponseCode {

    SUCCESS(200),
    FAIL(400),
    FAIL_WRONG_INPUT(401);

    private final Integer code;

    ResponseCode(Integer code) {
        this.code = code;
    }

    public Integer code() {
        return code;
    }
}
