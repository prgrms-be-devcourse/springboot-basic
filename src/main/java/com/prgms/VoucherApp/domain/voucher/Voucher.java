package com.prgms.VoucherApp.domain.voucher;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class Voucher {
    private final UUID voucherId;

    protected BigDecimal amount;

    protected VoucherType voucherType;

    public Voucher(UUID voucherId, BigDecimal amount, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    abstract BigDecimal discount(BigDecimal beforeAmount);

    public UUID getVoucherId() {
        return voucherId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
