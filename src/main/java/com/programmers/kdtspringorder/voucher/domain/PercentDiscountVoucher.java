package com.programmers.kdtspringorder.voucher.domain;

import com.programmers.kdtspringorder.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private UUID customerId;
    private final long percent;
    private final VoucherType type;
    private boolean used;
    private LocalDateTime createdAt;
    private LocalDateTime expirationDate;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this(voucherId, null, percent, VoucherType.PERCENT, false, LocalDateTime.now(), null);
    }

    public PercentDiscountVoucher(UUID voucherId, UUID customerId, long percent, VoucherType type, boolean used, LocalDateTime createdAt, LocalDateTime expirationDate) {
        if (percent <= 0 || percent > 50) {
            throw new IllegalArgumentException("percent should over than 0 And less or equal to 50");
        }
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.percent = percent;
        this.type = type;
        this.used = used;
        this.createdAt = createdAt;
        this.expirationDate = expirationDate;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - beforeDiscount * percent / 100;
    }

    @Override
    public void allocateVoucherToCustomer(UUID customerId) {
        this.customerId = customerId;
    }

    @Override
    public void removeVoucherFromCustomer() {
        customerId = null;
    }

    @Override
    public void use() {
        used = true;
    }

    @Override
    public long getValue() {
        return percent;
    }

    @Override
    public VoucherType getType() {
        return type;
    }

    public boolean isUsed() {
        return used;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    @Override
    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "voucherId=" + voucherId +
                ", discountPercent=" + percent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PercentDiscountVoucher that = (PercentDiscountVoucher) o;

        if (percent != that.percent) return false;
        return voucherId.equals(that.voucherId);
    }

    @Override
    public int hashCode() {
        int result = voucherId.hashCode();
        result = 31 * result + (int) (percent ^ (percent >>> 32));
        return result;
    }
}
