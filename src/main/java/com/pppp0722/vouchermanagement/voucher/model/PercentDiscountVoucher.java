package com.pppp0722.vouchermanagement.voucher.model;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;
    private final VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;
    private final UUID memberId;

    public PercentDiscountVoucher(UUID voucherId, long amount, UUID memberId) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.memberId = memberId;
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
    public UUID getMemberId() {
        return memberId;
    }

    @Override
    public VoucherType getType() {
        return voucherType;
    }
}
