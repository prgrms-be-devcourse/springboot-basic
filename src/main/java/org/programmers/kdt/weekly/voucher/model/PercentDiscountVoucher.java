package org.programmers.kdt.weekly.voucher.model;


import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private long percent;
    private final LocalDateTime createdAt;
    private static final VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;

    public PercentDiscountVoucher(UUID voucherId, int percent, LocalDateTime createdAt) {
        if (percent <= 0 || percent >= 100) {
            throw new IllegalArgumentException();
        }
        this.voucherId = voucherId;
        this.percent = percent;
        this.createdAt = createdAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public long getValue() {
        return percent;
    }

    @Override
    public void changeValue(int value) {
        this.percent = value;
    }

    @Override
    public String toString() {
        return "Voucher Type: " + voucherType +
            ", voucherId: " + voucherId +
            ", percent: " + percent + "%, createdAt: " + createdAt;
    }

    @Override
    public String serializeVoucher() {
        return voucherId + "," + voucherType + "," + percent + "," + createdAt;
    }
}