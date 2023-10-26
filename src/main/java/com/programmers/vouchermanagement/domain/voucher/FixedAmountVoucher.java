package com.programmers.vouchermanagement.domain.voucher;

import com.programmers.vouchermanagement.common.ErrorMessage;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID voucherId, String voucherName, float discountAmount, LocalDateTime createdAt) {
        super(voucherId, voucherName, discountAmount, createdAt);
        this.validateDiscountAmount(discountAmount);
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
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
        return String.join(separator, id.toString(), name, String.valueOf(discountAmount), createdAt.toString(), VoucherType.FIXED.name());
    }

    @Override
    public String toString() {
        return System.lineSeparator() +
                "++++++ Fixed Amount voucher ++++++" + System.lineSeparator() +
                "Voucher Id:    " + id + System.lineSeparator() +
                "Voucher Name:  " + name + System.lineSeparator() +
                "Discount Amount: " + discountAmount + "$" + System.lineSeparator() +
                "Created Time: " + createdAt + System.lineSeparator();
    }
}
