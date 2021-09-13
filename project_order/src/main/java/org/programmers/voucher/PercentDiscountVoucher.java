package org.programmers.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;
    private final VoucherType voucherType = VoucherType.PERCENT;
    private UUID ownerId;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent < 0) throw new IllegalArgumentException("Percent should be positive");
        if (percent == 0) throw new IllegalArgumentException("Percent should not be zero");
        if (percent > 100) throw new IllegalArgumentException("Percent should not exceed 100");

        this.voucherId = voucherId;
        this.percent = percent;
        this.ownerId = null;
    }

    public PercentDiscountVoucher(UUID voucherId, long amount, UUID ownerId) {
        if (amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("Amount should not be zero ");

        this.voucherId = voucherId;
        this.percent = amount;
        this.ownerId = ownerId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getValue() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * (1 - (double) percent / 100));
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }

}
