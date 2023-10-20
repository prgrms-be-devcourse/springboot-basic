package com.programmers.springbootbasic.domain.voucher.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMsg {
    wrongFixedAmountValueInput("할인값은 0보다 작을 수 없습니다."),
    wrongPercentDiscountValueInput("할인율은 0~100% 사이여야 합니다."),
    wrongVoucherTypeNumber("잘못된 Voucher Type 선택입니다."),
    voucherNotFound("Voucher가 존재하지 않습니다."),
    numberFormatMismatch("잘못된 숫자 형식입니다."),
    UUIDFormatMismatch("잘못된 UUID 형식입니다.");
    private final String message;
}
