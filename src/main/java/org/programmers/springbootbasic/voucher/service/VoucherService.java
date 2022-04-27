package org.programmers.springbootbasic.voucher.service;

import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.domain.VoucherType;

import java.util.List;
import java.util.UUID;

public interface VoucherService {

    Voucher createVoucher(int amount, VoucherType voucherType);

    void registerVouchersOwner(UUID voucherId, Long memberId);

    Voucher getVoucher(UUID voucherId);

    long applyVoucher(long beforeDiscount, Voucher voucher);

    void deleteVoucher(UUID voucherId);

    List<Voucher> getVouchersByType(VoucherType type);

    List<Voucher> getAllVouchers();

    boolean isValidAmount(int amount, VoucherType voucherType);
}
