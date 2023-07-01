package com.devcourse.springbootbasic.application.domain.voucher;

import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.dto.DiscountValue;

import java.util.UUID;

public abstract class Voucher {
    protected UUID voucherId;
    protected VoucherType voucherType;
    protected DiscountValue discountValue;

    Voucher(UUID voucherId, VoucherType voucherType, DiscountValue discountAmount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountValue = discountAmount;
    }

    public abstract double discountedPrice(long originalPrice);

    public abstract UUID getVoucherId();

    public abstract VoucherType getVoucherType();

    public abstract DiscountValue getDiscountValue();

    public abstract String toString();
}
