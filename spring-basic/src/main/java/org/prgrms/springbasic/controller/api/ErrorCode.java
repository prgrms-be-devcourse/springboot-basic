package org.prgrms.springbasic.controller.api;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Entity Not Found"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST.value(), "Invalid Type Value"),
    SERVICE_NOT_SUPPORT(HttpStatus.SERVICE_UNAVAILABLE.value(), "Service unavailable");

    private final int statusCode;
    private final String errorMessage;


    ErrorCode(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
