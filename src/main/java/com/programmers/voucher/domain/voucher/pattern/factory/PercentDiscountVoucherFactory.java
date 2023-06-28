package com.programmers.voucher.domain.voucher.pattern.factory;

import com.programmers.voucher.domain.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.domain.Voucher;

import java.util.UUID;

public class PercentDiscountVoucherFactory implements VoucherFactory {
    @Override
    public Voucher publishVoucher(UUID voucherId, long amount) {
        return new PercentDiscountVoucher(voucherId, amount);
    }
}
