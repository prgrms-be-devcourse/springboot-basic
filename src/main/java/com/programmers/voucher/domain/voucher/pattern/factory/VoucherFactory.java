package com.programmers.voucher.domain.voucher.pattern.factory;

import com.programmers.voucher.domain.voucher.domain.Voucher;

import java.util.UUID;

public interface VoucherFactory {
    Voucher publishVoucher(UUID voucherId, long amount);
}
