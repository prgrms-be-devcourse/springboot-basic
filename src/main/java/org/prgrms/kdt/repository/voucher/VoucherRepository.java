package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    Voucher update(Voucher voucher);
    void delete(UUID voucherId);
    List<Voucher> findAll();
}
