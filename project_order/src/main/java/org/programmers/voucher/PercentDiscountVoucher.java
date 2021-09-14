package org.programmers.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discountValue;
    private final VoucherType voucherType = VoucherType.PERCENT;
    private UUID ownerId;

    public PercentDiscountVoucher(UUID voucherId, long discountValue) {
        if (discountValue < 0) throw new IllegalArgumentException("Percent should be positive");
        if (discountValue == 0) throw new IllegalArgumentException("Percent should not be zero");
        if (discountValue > 100) throw new IllegalArgumentException("Percent should not exceed 100");

        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.ownerId = null;
    }

    public PercentDiscountVoucher(UUID voucherId, long discountValue, UUID ownerId) {
        if (discountValue < 0) throw new IllegalArgumentException("Amount should be positive");
        if (discountValue == 0) throw new IllegalArgumentException("Amount should not be zero ");

        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.ownerId = ownerId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getDiscountValue() {
        return discountValue;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public UUID getOwnerId() {
        return ownerId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * (1 - (double) discountValue / 100));
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + discountValue +
                '}';
    }

}
