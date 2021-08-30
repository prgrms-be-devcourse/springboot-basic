package org.prgrms.kdt.kdtspringorder.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{

    private static final long MAX_VOUCHER_AMOUNT = 10000;

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        checkAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    private void checkAmount(long amount) {
        if(amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if(amount == 0) throw new IllegalArgumentException("Amount should not be zero");
        if(amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException(String.format("Amount should be less than %d", MAX_VOUCHER_AMOUNT));
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - amount;
        return ( discountedAmount < 0 ) ? 0 : discountedAmount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
            "voucherId=" + voucherId +
            ", amount=" + amount +
            '}';
    }

}
