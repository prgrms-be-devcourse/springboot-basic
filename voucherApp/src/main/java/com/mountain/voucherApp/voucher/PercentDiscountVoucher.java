package com.mountain.voucherApp.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;
    private static final long MAX_VOUCHER_AMOUNT = 100;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent <= 0)
            throw new IllegalArgumentException("Amount should be positive");
        if (percent > MAX_VOUCHER_AMOUNT)
            throw new IllegalArgumentException("Amount should be less than " + MAX_VOUCHER_AMOUNT);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public PercentDiscountVoucher(long percent) {
        PercentDiscountVoucher instance = new PercentDiscountVoucher(UUID.randomUUID(), percent);
        this.voucherId = instance.getVoucherId();
        this.percent = instance.getAmount();
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        double discountAmount = Math.round(percent * 0.01 * beforeDiscount);
        long result = beforeDiscount - (long)discountAmount;
        return result;
    }

    @Override
    public long getAmount() {
        return percent;
    }

    @Override
    public String toString() {
        return "id: " + voucherId + ", percent: " + percent;
    }
}
