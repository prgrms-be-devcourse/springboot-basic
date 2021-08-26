package org.programmers.kdt.voucher.service;

import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherService {
    Optional<Voucher> getVoucher(UUID voucherId);
    void useVoucher(Voucher voucher);
    void deleteVoucher(UUID voucherId);
    Voucher createVoucher(VoucherType voucherType, UUID voucherId, long discount);
    List<Voucher> getAllVouchers();
}
