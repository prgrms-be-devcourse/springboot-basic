package com.devcourse.springbootbasic.engine.voucher.domain;

import com.devcourse.springbootbasic.engine.model.VoucherType;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    FixedAmountVoucher(UUID uuid, VoucherType voucherType, double amount) {
        super(uuid, voucherType, amount);
    }

    @Override
    public double discountedPrice(long originalPrice) {
        return originalPrice - discountValue;
    }

}
