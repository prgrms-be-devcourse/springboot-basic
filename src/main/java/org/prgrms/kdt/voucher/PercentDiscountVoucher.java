package org.prgrms.kdt.voucher;

import lombok.ToString;

import java.util.UUID;

@ToString
public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_VOUCHER_DISCOUNT_RATE = 100;

    private final UUID voucherId;
    private final int discountRate;

    public PercentDiscountVoucher(UUID voucherId, int discountRate) {
        if (discountRate < 0)
            throw new IllegalArgumentException("Percent should be positive");
        if (discountRate == 0)
            throw new IllegalArgumentException("Percent should not be zero");
        if (discountRate >= 100)
            throw new IllegalArgumentException("Amount should be less than %d".formatted(MAX_VOUCHER_DISCOUNT_RATE));
        this.voucherId = voucherId;
        this.discountRate = discountRate;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getType() {
        return VoucherType.PercentDiscountVoucher;
    }

    @Override
    public long getDiscount() {
        return discountRate;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (int) (beforeDiscount * (discountRate / 100.0));
    }
}
