package org.programmers.kdt.voucher.repository;

import org.programmers.kdt.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
    default Optional<Voucher> deleteVoucher(Voucher voucher) {
        return deleteVoucher(voucher.getVoucherId());
    }
    Optional<Voucher> deleteVoucher(UUID voucherId);
    List<Voucher> findAll();
}
