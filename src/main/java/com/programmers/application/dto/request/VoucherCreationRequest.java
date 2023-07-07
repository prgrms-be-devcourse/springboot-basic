package com.programmers.application.dto.request;

import java.util.Objects;

public record VoucherCreationRequest(String voucherType, long discountAmount) {
    public static final int NO_DISCOUNT_AMOUNT = 0;

    public VoucherCreationRequest {
        validateVoucherCreationRequest(voucherType, discountAmount);
    }

    private void validateVoucherCreationRequest(String voucherType, long discountAmount) {
        if (Objects.isNull(voucherType) || voucherType.isBlank()|| discountAmount == NO_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("바우처 타입과 할인양을 입력해주세요.");
        }
    }
}
