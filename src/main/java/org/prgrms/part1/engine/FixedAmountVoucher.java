package org.prgrms.part1.engine;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;

    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public Long getDiscount() {
        return amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FixedAmount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }
}
