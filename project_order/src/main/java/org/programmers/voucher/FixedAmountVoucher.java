package org.programmers.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discountValue;
    private final VoucherType voucherType = VoucherType.FIXED;
    private UUID ownerId;

    public FixedAmountVoucher(UUID voucherId, long discountValue) {
        if (discountValue < 0) throw new IllegalArgumentException("Amount should be positive");
        if (discountValue == 0) throw new IllegalArgumentException("Amount should not be zero ");

        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.ownerId = null;
    }

    public FixedAmountVoucher(UUID voucherId, long discountValue, UUID ownerId) {
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

    public long discount(long beforeDiscount) {
        return beforeDiscount - discountValue;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + discountValue +
                '}';
    }

}
