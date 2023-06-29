package com.devcourse.springbootbasic.application.domain;

import com.devcourse.springbootbasic.application.dto.VoucherType;

import java.text.MessageFormat;
import java.util.UUID;

public abstract class Voucher {
    UUID voucherId;
    VoucherType voucherType;
    double discountValue;

    Voucher(UUID voucherId, VoucherType voucherType, double discountAmount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountValue = discountAmount;
    }

    abstract double discountedPrice(long originalPrice);

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public abstract String toString();
}
