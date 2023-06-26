package org.prgrms.kdt.voucher.model;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final UUID voucherId;
    private final long amount;
    private final String voucherName;
    private static int voucherNum = 0;
    private final int voucherTypeNum = 1;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("Amount should not be zero");
        if (amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException("Amount should be less than " + MAX_VOUCHER_AMOUNT);
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherName = getClass().getSimpleName() + voucherNum++;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }

    @Override
    public long getBenefit() {
        return amount;
    }

    @Override
    public int getVoucherTypeNum() {
        return voucherTypeNum;
    }

    @Override
    public String getVoucherName() {
        return voucherName;
    }
}
