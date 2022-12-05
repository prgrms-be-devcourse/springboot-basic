package org.prgrms.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    NOT_FOUND_VOUCHER(HttpStatus.NOT_FOUND, "바우처를 찾을 수 없습니다");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
