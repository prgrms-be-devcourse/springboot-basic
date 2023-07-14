package com.programmers.voucher.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_COMMAND(HttpStatus.BAD_REQUEST, "존재하지 않는 명령어입니다."),
    INVALID_DISCOUNT_AMOUNT(HttpStatus.BAD_REQUEST, "할인 값은 양수만 가능합니다."),
    INVALID_DISCOUNT_PERCENT(HttpStatus.BAD_REQUEST, "할인율은 100을 넘을 수 없습니다."),
    NOT_FOUND_VOUCHER(HttpStatus.NOT_FOUND, "존재하는 바우처가 없습니다."),
    NOT_FOUND_CUSTOMER(HttpStatus.NOT_FOUND, "존재하는 고객이 없습니다."),
    EXISTED_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다.");

    private final HttpStatus status;
    private final String message;
}
