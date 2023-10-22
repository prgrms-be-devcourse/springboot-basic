package com.programmers.vouchermanagement.domain.voucher;

import com.programmers.vouchermanagement.common.ErrorMessage;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID voucherId, String voucherName, float discountAmount) {
        super(voucherId, voucherName, discountAmount);
        validateDiscountAmount(discountAmount);
    }

    @Override
    public UUID getId() {
        return this.voucherId;
    }

    @Override
    public float discount(float beforeDiscount) {
        return beforeDiscount - (beforeDiscount * discountAmount / 100);
    }

    @Override
    public void validateDiscountAmount(float discountAmount) {
        if (discountAmount <= 0 || discountAmount > 100) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PERCENTAGE_DISCOUNT_AMOUNT.getMessage());
        }
    }

    @Override
    public String joinInfo(String separator) {
        return String.join(separator, voucherId.toString(), voucherName, String.valueOf(discountAmount), VoucherType.PERCENTAGE.name());
    }

    @Override
    public String toString() {
        return System.lineSeparator() +
                "+++++++++++++++++++++++" + System.lineSeparator() +
                "Voucher Id:    " + voucherId + System.lineSeparator() +
                "Voucher Name:  " + voucherName + System.lineSeparator() +
                "Voucher Type:  Percentage Discount voucher" + System.lineSeparator() +
                "Discount percentage: " + discountAmount + "%" + System.lineSeparator();
    }
}
