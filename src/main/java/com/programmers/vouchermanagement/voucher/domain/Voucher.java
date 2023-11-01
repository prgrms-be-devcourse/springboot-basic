package com.programmers.vouchermanagement.voucher.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final BigDecimal discountValue;
    private final VoucherType voucherType;
    private final UUID customerId;

    public Voucher(UUID voucherId, BigDecimal discountValue, VoucherType voucherType) {
        this(voucherId, discountValue, voucherType, null);
    }

    public Voucher(UUID voucherId, BigDecimal discountValue, VoucherType voucherType, UUID customerId) {
        voucherType.validateDiscountValue(discountValue);
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
        this.customerId = customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
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
}
