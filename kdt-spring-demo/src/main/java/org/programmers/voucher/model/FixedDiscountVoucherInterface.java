package org.programmers.voucher.model;

import java.util.UUID;

public class FixedDiscountVoucherInterface implements VoucherInterface {
    private final UUID voucherId;
    private final long amount;

    public FixedDiscountVoucherInterface(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public long getVoucherValue() {
        return amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }
}
