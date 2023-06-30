package com.devcourse.springbootbasic.application.domain;

import com.devcourse.springbootbasic.application.dto.VoucherType;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID uuid, VoucherType voucherType, double amount) {
        super(uuid, voucherType, amount);
    }

    @Override
    public double discountedPrice(long originalPrice) {
        return originalPrice - discountValue;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}(id: {1}, {2}: {3} amount)", voucherType.name(), voucherId, voucherType.getTypeString(), discountValue);
    }

}
