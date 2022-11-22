package org.prgrms.springorder.global.controller;

public class ErrorResponse {

    private String errorMessage;

    private int statusCode;

    private String requestUri;

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public ErrorResponse(String errorMessage, int statusCode, String uri) {
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
        this.requestUri = uri;
    }
}
