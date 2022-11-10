package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    Voucher getVoucherById(UUID voucherId);
    List<Voucher> getAll();
}
