package com.devcourse.springbootbasic.engine.voucher.domain.factory;

import com.devcourse.springbootbasic.engine.model.VoucherType;
import com.devcourse.springbootbasic.engine.voucher.domain.PercentDiscountVoucher;
import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;

import java.util.UUID;

public class PercentVoucherFactory implements VoucherFactory {
    @Override
    public Voucher create(VoucherType voucherType, double voucherDiscount) {
        return new PercentDiscountVoucher(
                UUID.randomUUID(),
                voucherType,
                voucherDiscount
        );
    }
}
