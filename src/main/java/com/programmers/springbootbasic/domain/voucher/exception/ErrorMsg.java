package com.programmers.springbootbasic.domain.voucher.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMsg {
    WRONG_FIXED_AMOUNT_VALUE_INPUT("할인값은 0보다 작을 수 없습니다."),
    WRONG_PERCENT_DISCOUNT_VALUE_INPUT("할인율은 0~100% 사이여야 합니다."),
    WRONG_VOUCHER_TYPE_NUMBER("잘못된 Voucher Type 선택입니다."),
    VOUCHER_NOT_FOUND("Voucher가 존재하지 않습니다."),
    NUMBER_FORMAT_MISMATCH("잘못된 숫자 형식입니다."),
    UUID_FORMAT_MISMATCH("잘못된 UUID 형식입니다.");
    private final String message;
}
