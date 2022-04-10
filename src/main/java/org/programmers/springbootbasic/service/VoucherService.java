package org.programmers.springbootbasic.service;

import org.programmers.springbootbasic.voucher.Voucher;

import java.util.UUID;

public interface VoucherService {

    Voucher registerVoucher(Voucher voucher);
    Voucher getVoucher(UUID voucherId);
    long applyVoucher(long beforeDiscount, Voucher voucher);
    void useVoucher(UUID voucherId);
}
