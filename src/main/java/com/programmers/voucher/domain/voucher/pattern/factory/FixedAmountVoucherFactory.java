package com.programmers.voucher.domain.voucher.pattern.factory;

import com.programmers.voucher.domain.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.domain.Voucher;

import java.util.UUID;

public class FixedAmountVoucherFactory implements VoucherFactory {
    @Override
    public Voucher publishVoucher(UUID voucherId, long amount) {
        return new FixedAmountVoucher(voucherId, amount);
    }
}
