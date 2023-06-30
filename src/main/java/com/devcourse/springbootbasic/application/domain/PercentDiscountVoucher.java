package com.devcourse.springbootbasic.application.domain;

import com.devcourse.springbootbasic.application.dto.VoucherType;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID uuid, VoucherType voucherType, double percent) {
        super(uuid, voucherType, percent);
    }

    @Override
    public double discountedPrice(long originalPrice) {
        return originalPrice - (1 - discountValue / (double) 100);
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}(id: {1}, {2}: {3} percent)", voucherType.name(), voucherId, voucherType.getTypeString(), discountValue);
    }

}
