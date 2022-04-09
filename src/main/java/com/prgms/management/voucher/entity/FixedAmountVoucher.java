package com.prgms.management.voucher.entity;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final Long amount;

    public FixedAmountVoucher(Long amount) {
        this.voucherId = UUID.randomUUID();
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long discount(Long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher {" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }
}
