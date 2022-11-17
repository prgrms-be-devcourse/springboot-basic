package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    boolean saveVoucher(Voucher voucher);
    Optional<Voucher> getVoucherById(UUID voucherId);
    List<Voucher> getAllVouchers();
}
