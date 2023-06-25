package com.programmers.voucher.dto.request;

import java.util.Objects;

public record VoucherCreationRequest(String voucherType, long discountAmount) {
    public VoucherCreationRequest {
        validateVoucherCreationRequest(voucherType, discountAmount);
    }

    private static void validateVoucherCreationRequest(String voucherType, long discountAmount) {
        if (Objects.isNull(voucherType) || discountAmount == 0) {
            throw new IllegalArgumentException("바우처 타입과 할인양을 입력해주세요.");
        }
    }

}