package org.prgrms.kdtspringvoucher.entity.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private UUID voucherId;
    private final long amount;
    private static final VoucherTypeNum voucherType = VoucherTypeNum.PERCENT;
    private final LocalDateTime createdAt;

    private static final long MAX_PERCENT_VOUCHER_AMOUNT = 100;

    public PercentDiscountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        if (amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if (amount == 0) throw new IllegalArgumentException("Amount should not be zero");
        if (amount > MAX_PERCENT_VOUCHER_AMOUNT) throw new IllegalArgumentException("Amount should be less than %d".formatted(MAX_PERCENT_VOUCHER_AMOUNT));
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
        return beforeDiscount * ((100 - amount) / 100);
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
        return "Percent Discount Voucher {" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                ", voucher type=" + voucherType.toString() +
                "}";
    }
}
