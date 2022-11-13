package com.programmers.commandline.domain.voucher.entity.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final Long percent;
    private final VoucherType vouchertype = VoucherType.PERCENT_DISCOUNT;
    private final String amountUnit = "%";

    public PercentDiscountVoucher(UUID voucherId, Long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public PercentDiscountVoucher(Long percent) {
        this.voucherId = UUID.randomUUID();
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public VoucherType getType() {
        return this.vouchertype;
    }

    @Override
    public Long getDiscount() {
        return this.percent;
    }

    @Override
    public String getAmountUnit() {
        return this.amountUnit;
    }
}
