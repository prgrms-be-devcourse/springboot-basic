package com.programmers.voucher.domain.voucher.pattern.factory;

import com.programmers.voucher.domain.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.domain.Voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucherFactory implements VoucherFactory {
    @Override
    public Voucher createVoucher(UUID voucherId, long amount) {
        return new FixedAmountVoucher(voucherId, amount);
    }

    @Override
    public Voucher retrieveVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        return new FixedAmountVoucher(voucherId, createdAt, amount);
    }
}
