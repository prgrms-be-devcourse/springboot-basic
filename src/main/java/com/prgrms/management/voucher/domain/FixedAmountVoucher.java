package com.prgrms.management.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final long amount;
    private final VoucherType voucherType;

    public FixedAmountVoucher(UUID voucherId, long amount,VoucherType voucherType) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType=voucherType;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }
}
