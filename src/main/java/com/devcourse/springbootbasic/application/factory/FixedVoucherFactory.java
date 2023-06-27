package com.devcourse.springbootbasic.application.factory;

import com.devcourse.springbootbasic.application.domain.FixedAmountVoucher;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.domain.Voucher;

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
