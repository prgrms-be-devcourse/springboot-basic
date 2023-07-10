package com.programmers.springbasic.domain.voucher.validator;

import lombok.Getter;


@Getter
public class PercentDiscountVoucherCreateRequestValidator {
    private static final String VALID_PERCENT_DISCOUNT_REGEX = "^(100|[1-9][0-9]?)$";   // 100 이하의 정수 허용
    private static final String INVALID_PERCENT_DISCOUNT_MESSAGE = "100 이하의 정수만 입력 가능합니다.";

    private String inputPercent;

    public PercentDiscountVoucherCreateRequestValidator(String inputPercent) {
        if (!inputPercent.matches(VALID_PERCENT_DISCOUNT_REGEX)) {
            throw new IllegalArgumentException(INVALID_PERCENT_DISCOUNT_MESSAGE);
        }

        this.inputPercent = inputPercent;
    }
}
