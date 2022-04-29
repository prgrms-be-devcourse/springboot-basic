package org.prgrms.kdtspringvoucher.voucher.entity;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private UUID voucherId;
    private final long amount;
    private static final VoucherTypeNum voucherTypeNum = VoucherTypeNum.FIXED;
    private static final long MAX_FIXED_VOUCHER_AMOUNT = 10000;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("Amount should not be zero");
        if (amount > MAX_FIXED_VOUCHER_AMOUNT) throw new IllegalArgumentException("Amount should be less than %d".formatted(MAX_FIXED_VOUCHER_AMOUNT));
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0 : discountedAmount;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public int getVoucherTypeNum() {
        return voucherTypeNum.ordinal();
    }

    @Override
    public String getStringForCSV() {
        return this.getClass().getCanonicalName() + "," + voucherId + "," + amount;
    }

    @Override
    public String toString() {
        return "Fixed Amount Voucher {" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                ", voucher type=" + voucherTypeNum.toString() +
                '}';
    }
}
