package com.programmers.VoucherManagementApplication.voucher;

import java.util.UUID;

public class Voucher {


    private final UUID voucherId;
    private final Long amount;
    private final Long originPrice;
    private final VoucherType voucherType;

    public Voucher(UUID voucherId, long originPrice, long amount, VoucherType voucherType) {
        if (amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("Amount should not be zero");
        if (amount > originPrice) throw new IllegalArgumentException("Amount should be less " + originPrice);

        this.voucherId = voucherId;
        this.originPrice = originPrice;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public long getOriginPrice() {
        return originPrice;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long discount() {
        return this.voucherType
                .getArithmetic()
                .apply(getOriginPrice(), getAmount());
    }
}
