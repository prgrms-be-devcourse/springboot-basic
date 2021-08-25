package org.programmers.kdt.voucher.factory;

import org.programmers.kdt.voucher.Voucher;

import java.util.UUID;

// TODO : Abstract Factory Pattern 적용하기
public interface VoucherFactory {
    Voucher createVoucher(UUID voucherId, long discount);
}
