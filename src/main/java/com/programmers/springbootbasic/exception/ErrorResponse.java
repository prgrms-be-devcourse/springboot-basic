package com.programmers.springbootbasic.exception;

import lombok.Builder;

public class ErrorResponse {

    private final String message;
    private final int status;
    private final String code;

    @Builder
    private ErrorResponse(String message, int status, String code) {
        this.message = message;
        this.status = status;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }
}
