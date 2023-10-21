package com.programmers.vouchermanagement.domain.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID voucherId, String voucherName, float discountAmount) {
        super(voucherId, voucherName, discountAmount);
    }

    @Override
    public UUID getId() {
        return voucherId;
    }

    @Override
    public float discount(float beforeDiscount) {
        float afterDiscount = beforeDiscount - discountAmount;
        return afterDiscount < 0 ? 0 : afterDiscount;
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
