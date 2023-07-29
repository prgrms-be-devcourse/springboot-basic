package org.programmers.VoucherManagement.voucher.domain;

import org.programmers.VoucherManagement.voucher.exception.VoucherException;

import static org.programmers.VoucherManagement.global.response.ErrorCode.NOT_INCLUDE_1_TO_100;


public class DiscountValue {
    private final int value;

    public DiscountValue(int value) {
        this.value = value;
    }

    public DiscountValue(int discountValue, DiscountType discountType) {
        validateValue(discountValue, discountType);
        this.value = discountValue;
    }

    public int getValue() {
        return value;
    }

    private void validateValue(int discountValue, DiscountType discountType) {

        if (discountType == DiscountType.PERCENT && (discountValue > 100 || discountValue < 0)) {
            throw new VoucherException(NOT_INCLUDE_1_TO_100);
        }
    }
}
