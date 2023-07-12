package com.programmers.vouchermanagement.voucher.exception;

import com.programmers.vouchermanagement.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum VoucherErrorCode implements ErrorCode {

    INVALID_DISCOUNT_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 할인 타입입니다."),
    VOUCHER_NOT_FOUND(HttpStatus.NOT_FOUND, "바우처를 찾을 수 없습니다."),
    INVALID_FIX_AMOUNT(HttpStatus.BAD_REQUEST, "고정할인금액은 최소 1원 이상이여야 합니다."),
    INVALID_PERCENT(HttpStatus.BAD_REQUEST, "할인률은 1에서 100 사이여야 합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
