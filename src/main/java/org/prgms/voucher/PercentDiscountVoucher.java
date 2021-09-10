package org.prgms.voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_VOUCHER_PERCENT = 100;


    private final UUID voucherId;
    private final long amount;


    public PercentDiscountVoucher(UUID voucherId, long amount, VoucherType voucherType) {
        if (amount < 0) throw new IllegalArgumentException("amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("amount should not be zero");
        if (amount > MAX_VOUCHER_PERCENT)
            throw new IllegalArgumentException("amount should be less than %d".formatted(MAX_VOUCHER_PERCENT));
        this.voucherId = voucherId;
        this.amount = amount;
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long) (beforeDiscount * (amount / (double) 100));
    }

    @Override
    public long getAmount() {
        return amount;
    }


    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT_DISCOUNT;
    }

    @Override
    public String toString() {
        return MessageFormat.format("[PercentDiscountVoucher] voucherId : {0}, amount : {1}", voucherId, amount);
    }

}
