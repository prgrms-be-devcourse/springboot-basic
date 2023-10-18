package com.prgms.vouchermanager.domain.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private final UUID id;

    private final long amount;

    private final VoucherType type ;

    public FixedAmountVoucher(UUID id, long amount) {
        this.id = id;
        this.amount = amount;
        type = VoucherType.FIXED_AMOUNT;
    }


    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public long getDiscountValue() {
        return amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED_AMOUNT;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }
}
