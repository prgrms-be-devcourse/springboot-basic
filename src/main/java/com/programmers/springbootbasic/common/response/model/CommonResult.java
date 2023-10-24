package com.programmers.springbootbasic.common.response.model;

import lombok.Getter;

@Getter
public class CommonResult {
    protected boolean isSuccess;
    protected String message;

    public CommonResult(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

}
