package com.programmers.vouchermanagement.domain.voucher;

import com.programmers.vouchermanagement.common.ErrorMessage;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID voucherId, String voucherName, float discountAmount) {
        super(voucherId, voucherName, discountAmount);
        this.validateDiscountAmount(discountAmount);
    }

    @Override
    public UUID getId() {
        return voucherId;
    }

    @Override
    public float discount(float beforeDiscount) {
        float afterDiscount = beforeDiscount - discountAmount;
        if (afterDiscount < 0) {
            throw new IllegalArgumentException(ErrorMessage.DISCOUNT_OVER_BEFORE_DISCOUNT.getMessage());
        }
        return afterDiscount;
    }

    @Override
    public void validateDiscountAmount(float discountAmount) {
        if (discountAmount <= 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FIXED_DISCOUNT_AMOUNT.getMessage());
        }
    }

    @Override
    public String joinInfo(String separator) {
        return String.join(separator, voucherId.toString(), voucherName, String.valueOf(discountAmount), VoucherType.FIXED.name());
    }

    @Override
    public String toString() {
        return System.lineSeparator() +
                "+++++++++++++++++++++++" + System.lineSeparator() +
                "Voucher Id:    " + voucherId + System.lineSeparator() +
                "Voucher Name:  " + voucherName + System.lineSeparator() +
                "Voucher Type:  Fixed amount voucher" + System.lineSeparator() +
                "Discount Amount:   " + discountAmount + System.lineSeparator();
    }
}
