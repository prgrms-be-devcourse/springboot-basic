package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    Optional<Voucher> saveVoucher(Voucher voucher);
    Optional<Voucher> getVoucherById(long voucherId);
    List<Voucher> getAllVouchers();
}
