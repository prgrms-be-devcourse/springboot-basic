package com.prgrms.devkdtorder.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID voucherId, long percent, String name, LocalDateTime createdAt) {
        super(voucherId, percent, name, createdAt);
        validatePercent(percent);
    }

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        this(voucherId, amount, "", LocalDateTime.now());
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * (1 - value/(double)100));
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENTDISCOUNT;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + value +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    private void validatePercent(long percent) {
        if (percent < 0) throw new IllegalArgumentException("Percent should be positive");
        if (percent == 0) throw new IllegalArgumentException("Percent should not be zero");
        if (percent > 100) throw new IllegalArgumentException("Percent should be less than 100");
    }
}

