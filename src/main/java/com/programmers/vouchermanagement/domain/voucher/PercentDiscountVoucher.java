package com.programmers.vouchermanagement.domain.voucher;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID voucherId, String voucherName, float discountAmount) {
        super(voucherId, voucherName, discountAmount);
    }

    @Override
    public UUID getId() {
        return this.voucherId;
    }

    @Override
    public float discount(float beforeDiscount) {
        float afterDiscount = beforeDiscount - (beforeDiscount * discountAmount / 100);
        return afterDiscount < 0 ? 0 : afterDiscount;
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
