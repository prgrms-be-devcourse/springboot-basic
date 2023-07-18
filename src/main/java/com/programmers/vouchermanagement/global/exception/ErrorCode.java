package com.programmers.vouchermanagement.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Voucher
    INVALID_DISCOUNT_TYPE(HttpStatus.BAD_REQUEST, "V001", "유효하지 않은 할인 타입입니다."),
    VOUCHER_NOT_FOUND(HttpStatus.NOT_FOUND, "V002", "바우처를 찾을 수 없습니다."),
    INVALID_FIX_AMOUNT(HttpStatus.BAD_REQUEST, "V003", "고정할인금액은 최소 1원 이상이여야 합니다."),
    INVALID_PERCENT(HttpStatus.BAD_REQUEST, "V004", "할인률은 1에서 100 사이여야 합니다."),

    // Customer
    INVALID_CUSTOMER_TYPE(HttpStatus.BAD_REQUEST, "C001", "유효하지 않은 고객 타입입니다."),

    // Common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S999", "Internal Server Error");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
