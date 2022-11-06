package com.programmers.springvoucherservice.domain.voucher;


import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long percentage;

    public PercentDiscountVoucher(UUID voucherId, long percentage) {
        this.voucherId = voucherId;
        this.percentage = percentage;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percentage / 100);
    }

    @Override
    public String toString() {
        return "type=PercentDiscountVoucher" + '\n' +
                "percentage=" + percentage;
    }

    @Override
    public long getValue() {
        return percentage;
    }
}
