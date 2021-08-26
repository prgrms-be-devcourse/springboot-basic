package org.programmers.kdt.voucher.service;

import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherService {
    Optional<Voucher> getVoucher(UUID voucherId);
    void useVoucher(Voucher voucher);
    Voucher createVoucher(VoucherType voucherType, UUID voucherId, long discount);
    default Optional<Voucher> removeVoucher(Voucher voucher) {
        return removeVoucher(voucher.getVoucherId());
    }

    Optional<Voucher> removeVoucher(UUID voucherid);
    List<Voucher> getAllVouchers();
}
