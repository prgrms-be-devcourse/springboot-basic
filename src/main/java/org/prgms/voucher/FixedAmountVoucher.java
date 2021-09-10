package org.prgms.voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 10000;

    private final UUID voucherId;
    private final long amount;
    private final VoucherType voucherType;

    public FixedAmountVoucher(UUID voucherId, long amount, VoucherType voucherType) {
        this.voucherType = voucherType;
        if (amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("Amount should not be zero");
        if (amount > MAX_VOUCHER_AMOUNT)
            throw new IllegalArgumentException("Amount should  be less than %d".formatted(MAX_VOUCHER_AMOUNT));
        this.voucherId = voucherId;
        this.amount = amount;
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return MessageFormat.format("[FixedAmountVoucher] voucherId : {0}, amount : {1}", voucherId, amount);
    }


    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        if (discountedAmount < 0) return 0;
        return discountedAmount;
    }

    @Override
    public long getAmount() {
        return amount;
    }


    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED_AMOUNT;
    }


}
