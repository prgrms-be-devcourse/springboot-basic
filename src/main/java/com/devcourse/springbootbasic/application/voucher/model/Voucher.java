package com.devcourse.springbootbasic.application.voucher.model;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final DiscountValue discountValue;
    private final Optional<UUID> customerId;

    public Voucher(UUID voucherId, VoucherType voucherType, DiscountValue discountAmount, Optional<UUID> customerId) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountValue = discountAmount;
        this.customerId = customerId;
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

    public Optional<UUID> getCustomerId() {
        return customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return Objects.equals(voucherId, voucher.voucherId) && voucherType == voucher.voucherType && Objects.equals(discountValue, voucher.discountValue) && Objects.equals(customerId, voucher.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, voucherType, discountValue, customerId);
    }

    @Override
    public String toString() {
        if (customerId.isEmpty()) {
            return MessageFormat.format("Voucher'{'voucherId={0}, voucherType={1}, discountValue={2}'}'",
                    voucherId,
                    voucherType,
                    discountValue.getValue());
        }
        return MessageFormat.format("Voucher'{'voucherId={0}, voucherType={1}, discountValue={2}, customerId={3}'}'",
                voucherId,
                voucherType,
                discountValue.getValue(),
                customerId);
    }

}
