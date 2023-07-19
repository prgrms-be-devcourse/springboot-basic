package com.tangerine.voucher_system.application.voucher.model;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public record Voucher(
        UUID voucherId,
        VoucherType voucherType,
        DiscountValue discountValue,
        LocalDate createdAt
) {

    public Price applyVoucher(Price originalPrice) {
        return originalPrice.applyDiscount(discountValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return Objects.equals(voucherId, voucher.voucherId) && voucherType == voucher.voucherType && Objects.equals(discountValue, voucher.discountValue) && Objects.equals(createdAt, voucher.createdAt);
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
