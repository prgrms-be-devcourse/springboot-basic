package com.programmers.voucher.domain.voucher.pattern.factory;

import com.programmers.voucher.domain.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.domain.Voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucherFactory implements VoucherFactory {
    @Override
    public Voucher publishVoucher(UUID voucherId, long amount) {
        return new PercentDiscountVoucher(voucherId, amount);
    }

    @Override
    public Voucher retrieveVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        return new PercentDiscountVoucher(voucherId, createdAt, amount);
    }
}
