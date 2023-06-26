package com.programmers.voucher.view.dto;

import com.programmers.voucher.constant.ErrorMessage;

public class DiscountAmount {
    private final long amount;

    public DiscountAmount(VoucherType voucherType, long amount) {
        this.amount = amount;
        validatePositive();
        validatePercent(voucherType);
    }

    private void validatePositive() {
        if (amount < 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DISCOUNT_AMOUNT);
            //TODO log 추가
        }
    }

    private void validatePercent(VoucherType voucherType) {
        if (voucherType == VoucherType.PERCENT_DISCOUNT && amount >= 100) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DISCOUNT_PERCENT);
        }
    }

    public long getAmount() {
        return amount;
    }
}
