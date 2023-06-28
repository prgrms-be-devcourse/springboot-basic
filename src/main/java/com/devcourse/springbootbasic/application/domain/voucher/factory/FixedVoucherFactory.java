package com.devcourse.springbootbasic.application.domain.voucher.factory;

import com.devcourse.springbootbasic.application.domain.voucher.data.FixedAmountVoucher;
import com.devcourse.springbootbasic.application.domain.voucher.dto.VoucherType;
import com.devcourse.springbootbasic.application.domain.voucher.data.Voucher;

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
