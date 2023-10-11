package com.programmers.springbootbasic.domain.voucher.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMsg {
    WrongFixedAmountValueInput("할인값은 0보다 작을 수 없습니다."),
    WrongPercentDiscountValueInput("할인율은 0~100% 사이여야 합니다."),
    WrongVoucherTypeNumber("잘못된 Voucher Type 선택입니다.");

    private final String message;
}
