package com.prgrms.springbootbasic.common.controller;

public class ResponseStatus {

    private final int statusCode;
    private final String message;

    public ResponseStatus(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
