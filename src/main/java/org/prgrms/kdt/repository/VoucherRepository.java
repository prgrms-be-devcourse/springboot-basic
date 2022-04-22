package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.voucher.Voucher;


import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository<K, V> {
    Voucher insert(Voucher voucher);

    Map<K, V> getVoucherList();

    Voucher delete(Voucher voucher);

    Optional<Voucher> getByVoucherId(UUID voucherId);
}
