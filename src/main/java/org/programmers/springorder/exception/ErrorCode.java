package org.programmers.springorder.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 장애로 인해 잠시 문제가 발생하였습니다." ),
    VOUCHER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 바우처를 찾을 수 없습니다."),
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 고객을 찾을 수 없습니다."),
    VOUCHER_OWNER_NOT_EXIST(HttpStatus.NOT_FOUND, "해당 바우처를 소유한 고객이 없습니다."),
    INVALID_DISCOUNT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 고정 할인 금액입니다."),
    INVALID_DISCOUNT_PERCENT(HttpStatus.BAD_REQUEST, "잘못된 할인율입니다."),

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다.") ;
    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return this.message;
    }
}
