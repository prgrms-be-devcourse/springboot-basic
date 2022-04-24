package org.prgrms.kdtspringvoucher.voucher.entity;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private UUID voucherId;
    private final long amount;
    private static final VoucherTypeNum voucherTypeNum = VoucherTypeNum.PERCENT;
    private static final long MAX_PERCENT_VOUCHER_AMOUNT = 100;

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("Amount should not be zero");
        if (amount > MAX_PERCENT_VOUCHER_AMOUNT) throw new IllegalArgumentException("Amount should be less than %d".formatted(MAX_PERCENT_VOUCHER_AMOUNT));
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * ((100 - amount) / 100);
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
        return "Percent Discount Voucher {" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                ", voucher type=" + voucherTypeNum.toString() +
                "}";
    }
}
