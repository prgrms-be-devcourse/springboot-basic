package com.programmers.vouchermanagement.customer.exception;

import com.programmers.vouchermanagement.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomerErrorCode implements ErrorCode {

    INVALID_CUSTOMER_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 고객 타입입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
