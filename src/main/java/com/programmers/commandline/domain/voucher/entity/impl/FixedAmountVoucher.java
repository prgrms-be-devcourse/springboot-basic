package com.programmers.commandline.domain.voucher.entity.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final Long amount;
    private final VoucherType type = VoucherType.FIXED_AMOUNT;
    private final UUID voucherId;
    private final String amountUnit = "$";

    public FixedAmountVoucher(long amount) {
        this.voucherId = UUID.randomUUID();
        this.amount = amount;
    }

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public VoucherType getType() {
        return this.type;
    }

    @Override
    public Long getDiscount() {
        return this.amount;
    }

    @Override
    public String getAmountUnit() {
        return this.amountUnit;
    }
}
