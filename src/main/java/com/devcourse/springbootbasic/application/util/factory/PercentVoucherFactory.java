package com.devcourse.springbootbasic.application.util.factory;

import com.devcourse.springbootbasic.application.domain.PercentDiscountVoucher;
import com.devcourse.springbootbasic.application.domain.Voucher;
import com.devcourse.springbootbasic.application.dto.VoucherType;

import java.util.UUID;

public class PercentVoucherFactory implements VoucherFactory {
    @Override
    public Voucher create(double voucherDiscount) {
        return new PercentDiscountVoucher(
                UUID.randomUUID(),
                VoucherType.PERCENT_DISCOUNT,
                voucherDiscount
        );
    }
}
