package com.devcourse.springbootbasic.application.domain.voucher.factory;

import com.devcourse.springbootbasic.application.domain.voucher.data.PercentDiscountVoucher;
import com.devcourse.springbootbasic.application.domain.voucher.data.Voucher;
import com.devcourse.springbootbasic.application.domain.voucher.dto.VoucherType;

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
