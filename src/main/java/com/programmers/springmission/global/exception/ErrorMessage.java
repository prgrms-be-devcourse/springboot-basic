package com.programmers.springmission.global.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    INVALID_OPTION_TYPE("유효하지 않은 메뉴입니다.\n"),
    INVALID_DISCOUNT_AMOUNT("잘못된 할인 범위입니다.\n"),
    INVALID_VOUCHER_TYPE("유효하지 않은 바우처입니다.\n");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}

