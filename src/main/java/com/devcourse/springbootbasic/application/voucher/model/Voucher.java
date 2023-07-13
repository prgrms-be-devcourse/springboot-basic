package com.devcourse.springbootbasic.application.voucher.model;

import java.text.MessageFormat;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final DiscountValue discountValue;
    private final UUID customerId;

    public Voucher(UUID voucherId, VoucherType voucherType, DiscountValue discountAmount, UUID customerId) {
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

    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Voucher'{'voucherId={0}, voucherType={1}, discountValue={2}, customerId={3}'}'",
                voucherId,
                voucherType,
                discountValue.getValue(),
                customerId);
    }
}
