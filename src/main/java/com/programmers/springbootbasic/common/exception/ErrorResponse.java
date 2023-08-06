package com.programmers.springbootbasic.common.exception;

public class ErrorResponse {
    private static final String BAD_REQUEST_MESSAGE = "잘못된 입력값입니다. 입력값을 확인하세요";
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "서버 내부 에러입니다.";
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse internalServerError() {
        return new ErrorResponse(INTERNAL_SERVER_ERROR_MESSAGE);
    }

    public static ErrorResponse badRequest() {
        return new ErrorResponse(BAD_REQUEST_MESSAGE);
    }

    public String getMessage() {
        return message;
    }
}
