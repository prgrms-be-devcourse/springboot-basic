package org.programmers.springbootbasic.voucher.service;

import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.domain.VoucherType;

import java.util.List;
import java.util.UUID;

public interface VoucherService {

    Voucher registerVoucher(int amount, VoucherType voucherType);

    Voucher getVoucher(UUID voucherId);

    long applyVoucher(long beforeDiscount, Voucher voucher);

    void useVoucher(UUID voucherId);

    List<Voucher> getAllVouchers();
}
