package com.programmers.kdtspringorder.voucher.domain;

import com.programmers.kdtspringorder.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private UUID customerId;
    private final long amount;
    private final VoucherType type;
    private boolean used;
    private LocalDateTime createdAt;
    private LocalDateTime expirationDate;

    private static final long MAX_VOUCHER_AMOUNT = 10000;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this(voucherId, null, amount, VoucherType.FIXED, false, LocalDateTime.now(), null);
    }

    public FixedAmountVoucher(UUID voucherId, UUID customerId, long amount, VoucherType type, boolean used, LocalDateTime createdAt, LocalDateTime expirationDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount should be positive");
        }
        if (amount > MAX_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException(String.format("Amount should be less than %d", MAX_VOUCHER_AMOUNT));
        }
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.amount = amount;
        this.type = type;
        this.used = used;
        this.createdAt = createdAt;
        this.expirationDate = expirationDate;
    }

    public void allocateVoucherToCustomer(UUID customerId) {
        this.customerId = customerId;
    }

    public void removeVoucherFromCustomer() {
        this.customerId = null;
    }

    @Override
    public void use() {
        used = true;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - amount;
        return discountedAmount < 0 ? 0 : discountedAmount;
    }

    @Override
    public long getValue() {
        return amount;
    }

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
                ", discountAmount=" + amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixedAmountVoucher that = (FixedAmountVoucher) o;

        if (amount != that.amount) return false;
        return voucherId.equals(that.voucherId);
    }

    @Override
    public int hashCode() {
        int result = voucherId.hashCode();
        result = 31 * result + (int) (amount ^ (amount >>> 32));
        return result;
    }

}
