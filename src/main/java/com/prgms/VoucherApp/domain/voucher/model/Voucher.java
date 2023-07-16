package com.prgms.VoucherApp.domain.voucher.model;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class Voucher {
    private final UUID voucherId;

    protected BigDecimal amount;

    public Voucher(UUID voucherId, BigDecimal amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    abstract BigDecimal discount(BigDecimal beforeAmount);

    public UUID getVoucherId() {
        return voucherId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public abstract VoucherType getVoucherType();

    @Override
    public String toString() {
        return "Voucher{" +
            "voucherId=" + voucherId +
            ", amount=" + amount +
            ", voucherType=" + getVoucherType() +
            '}';
    }
}
