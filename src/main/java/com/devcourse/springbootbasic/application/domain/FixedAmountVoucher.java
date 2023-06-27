package com.devcourse.springbootbasic.application.domain;

import com.devcourse.springbootbasic.application.dto.VoucherType;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID uuid, VoucherType voucherType, double amount) {
        super(uuid, voucherType, amount);
    }

    @Override
    public double discountedPrice(long originalPrice) {
        return originalPrice - discountValue;
    }

}
