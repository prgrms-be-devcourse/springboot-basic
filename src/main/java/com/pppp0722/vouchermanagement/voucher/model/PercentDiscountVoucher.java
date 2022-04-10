package com.pppp0722.vouchermanagement.voucher.model;

import com.pppp0722.vouchermanagement.voucher.model.Voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public String getVoucherType() {
        return "percent discount";
    }
}
