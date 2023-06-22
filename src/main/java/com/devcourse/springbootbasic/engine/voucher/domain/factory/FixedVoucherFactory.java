package com.devcourse.springbootbasic.engine.voucher.domain.factory;

import com.devcourse.springbootbasic.engine.model.VoucherType;
import com.devcourse.springbootbasic.engine.voucher.domain.FixedAmountVoucher;
import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;

import java.util.UUID;

public class FixedVoucherFactory implements VoucherFactory {

    @Override
    public Voucher create(double voucherDiscount) {
        return new FixedAmountVoucher(
                UUID.randomUUID(),
                VoucherType.FIXED_AMOUNT,
                voucherDiscount
        );
    }
}
