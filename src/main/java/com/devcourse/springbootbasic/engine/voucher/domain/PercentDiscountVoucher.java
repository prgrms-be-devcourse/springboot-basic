package com.devcourse.springbootbasic.engine.voucher.domain;

import com.devcourse.springbootbasic.engine.model.VoucherType;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    PercentDiscountVoucher(UUID uuid, VoucherType voucherType, double percent) {
        super(uuid, voucherType, percent);
    }

    @Override
    public double discountedPrice(long originalPrice) {
        return originalPrice - (1 - discountValue / (double) 100);
    }

}
