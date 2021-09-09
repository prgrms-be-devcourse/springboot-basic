package org.prgrms.kdt.voucher.domain;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 1000000;
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        validate(voucherId, amount);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - amount;
        return discountedAmount < 0 ? 0 : discountedAmount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("[FixedAmountVoucher - Discount Amount : {0}원]", amount);
    }

    public long getAmount() {
        return amount;
    }

    private void validate(UUID voucherId, long amount){
        if (amount < 0) {
            throw new IllegalArgumentException("Amount should be positive");
        }
        if(amount == 0){
            throw new IllegalArgumentException("Amount should not be zero");
        }
        if (amount > MAX_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("Amount should be less than " + MAX_VOUCHER_AMOUNT);
        }
    }
}
