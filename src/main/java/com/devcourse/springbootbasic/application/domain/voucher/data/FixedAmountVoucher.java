package com.devcourse.springbootbasic.application.domain.voucher.data;

import com.devcourse.springbootbasic.application.domain.voucher.dto.VoucherType;

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
