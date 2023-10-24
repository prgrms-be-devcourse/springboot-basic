package com.programmers.vouchermanagement.voucher.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final BigDecimal discountValue;
    private final VoucherType voucherType;

    public Voucher(UUID voucherId, BigDecimal discountValue, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.voucherType.validateDiscountValue(discountValue);
        this.discountValue = discountValue;
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
}
