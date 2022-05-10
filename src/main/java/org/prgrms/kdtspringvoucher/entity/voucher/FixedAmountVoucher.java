package org.prgrms.kdtspringvoucher.entity.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private UUID voucherId;
    private final long amount;
    private static final VoucherTypeNum voucherType = VoucherTypeNum.FIXED;
    private final LocalDateTime createdAt;

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        if (amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("Amount should not be zero");
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
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
        return voucherType.ordinal() + 1;
    }
    public VoucherTypeNum getVoucherType() {
        return voucherType;
    }
    @Override
    public String getStringForCSV() {
        return this.getClass().getCanonicalName() + "," + voucherId + "," + amount;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Fixed Amount Voucher {" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                ", voucher type=" + voucherType.toString() +
                '}';
    }
}
