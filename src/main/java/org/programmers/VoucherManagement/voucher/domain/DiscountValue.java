package org.programmers.VoucherManagement.voucher.domain;

import org.programmers.VoucherManagement.voucher.exception.VoucherException;

import static org.programmers.VoucherManagement.global.response.ErrorCode.NOT_INCLUDE_1_TO_100;


public class DiscountValue {
    private final int value;

    public DiscountValue(int value) {
        this.value = value;
    }

    public DiscountValue(int value, DiscountType discountType) {
        validateValue(discountType);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private void validateValue(DiscountType discountType) {
        if (discountType == DiscountType.PERCENT && (value > 100 || value < 0)) {
            throw new VoucherException(NOT_INCLUDE_1_TO_100);
        }
    }
}
