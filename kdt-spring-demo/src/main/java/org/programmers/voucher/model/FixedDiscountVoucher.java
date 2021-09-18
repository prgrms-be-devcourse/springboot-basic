package org.programmers.voucher.model;

import java.util.UUID;

public class FixedDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedDiscountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Voucher ID : " + voucherId + " / " + "Amount or Percent : " + amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
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
