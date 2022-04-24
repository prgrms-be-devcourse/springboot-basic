package org.prgms.voucheradmin.global.exception;

public class ErrorResponseDto {
    private int status;
    private String message;

    public ErrorResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorResponseDto of(int status, String message) {
        return new ErrorResponseDto(status, message);
    }
}
