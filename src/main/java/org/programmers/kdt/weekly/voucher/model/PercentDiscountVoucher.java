package org.programmers.kdt.weekly.voucher.model;


import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public final class PercentDiscountVoucher implements Voucher {
    private UUID voucherId;
    private final VoucherType voucherType = VoucherType.PercentDiscountVoucher;
    private int value;

    public PercentDiscountVoucher(UUID voucherId, int percent) {
        this.voucherId = voucherId;
        this.value = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (value / 100);
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Voucher Type: " + voucherType +
                ", voucherId: " + voucherId  +
                ", percent: " + value + "%";
    }

}
