package org.prgrms.kdt.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 10000;

    private final UUID voucherId;
    private long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getType() {
        return VoucherType.FixedAmountVoucher;
    }

    @Override
    public long getDiscount() {
        return amount;
    }

    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }

    @Override
    public void validateDiscount(long discount) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount should be positive");
        if (amount == 0)
            throw new IllegalArgumentException("Amount should not be zero");
        if (amount > MAX_VOUCHER_AMOUNT)
            throw new IllegalArgumentException("Amount should be less than %d".formatted(MAX_VOUCHER_AMOUNT));
    }

    @Override
    public void changeDiscount(long discount) {
        validateDiscount(discount);
        this.amount = discount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }
}
