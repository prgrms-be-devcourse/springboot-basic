package com.programmers.springmission.global.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    INVALID_OPTION_TYPE("유효하지 않은 메뉴입니다.\n"),

    INVALID_DISCOUNT_AMOUNT("잘못된 할인 범위입니다.\n"),
    INVALID_VOUCHER_TYPE("유효하지 않은 바우처 타입입니다.\n"),
    INVALID_CUSTOMER_FIND_TYPE("유효하지 않은 조회 타입입니다.\n"),

    NOT_EXIST_VOUCHER("존재하지 않는 바우처입니다.\n"),
    NOT_EXIST_CUSTOMER("존재하지 않는 고객입니다.\n"),

    DUPLICATE_ASSIGN_VOUCHER("이미 고객에게 할당된 바우처입니다.\n"),
    DUPLICATE_CUSTOMER_EMAIL("이미 존재하는 이메일입니다.\n");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}

