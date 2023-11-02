package com.programmers.vouchermanagement.voucher.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final LocalDateTime createdAt;
    private final BigDecimal discountValue;
    private final VoucherType voucherType;
    private final UUID customerId;

    public Voucher(UUID voucherId, BigDecimal discountValue, VoucherType voucherType) {
        this(voucherId, LocalDateTime.now(), discountValue, voucherType, null);
    }

    public Voucher(UUID voucherId, BigDecimal discountValue, VoucherType voucherType, UUID customerId) {
        this(voucherId, LocalDateTime.now(), discountValue, voucherType, customerId);
    }

    public Voucher(UUID voucherId, LocalDateTime createdAt, BigDecimal discountValue, VoucherType voucherType, UUID customerId) {
        voucherType.validateDiscountValue(discountValue);
        this.voucherId = voucherId;
        this.createdAt = createdAt;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
        this.customerId = customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public boolean isOwned() {
        return this.customerId != null;
    }

    public boolean isSameType(VoucherType voucherType) {
        return this.voucherType == voucherType;
    }

    public boolean isCreatedInBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (createdAt.compareTo(startDateTime) != 1 && createdAt.compareTo(endDateTime) != -1) {
            return true;
        }

        return false;
    }
}
