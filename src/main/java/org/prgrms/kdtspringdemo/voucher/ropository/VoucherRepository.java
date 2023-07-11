package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    Voucher findById(UUID voucherId);
    List<Voucher> findAll();
    Voucher update(Voucher voucher);
}
