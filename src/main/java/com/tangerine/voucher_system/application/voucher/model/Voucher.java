package com.tangerine.voucher_system.application.voucher.model;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final DiscountValue discountValue;
    private final LocalDate createdAt;

    public Voucher(UUID voucherId, VoucherType voucherType, DiscountValue discountAmount, LocalDate createdAt) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountValue = discountAmount;
        this.createdAt = createdAt;
    }

    public Price applyVoucher(Price originalPrice) {
        return originalPrice.applyDiscount(discountValue);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public DiscountValue getDiscountValue() {
        return discountValue;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return Objects.equals(voucherId, voucher.voucherId) && voucherType == voucher.voucherType && Objects.equals(discountValue, voucher.discountValue) && Objects.equals(createdAt, voucher.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, voucherType, discountValue, createdAt);
    }

    @Override
    public String toString() {
        return MessageFormat.format("Voucher'{'voucherId={0}, voucherType={1}, discountValue={2}, createdAt={3}'}'",
                voucherId,
                voucherType,
                discountValue.getValue(),
                createdAt);
    }

}
