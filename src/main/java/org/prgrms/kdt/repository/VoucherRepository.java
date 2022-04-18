package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.voucher.Voucher;


import java.util.Map;

public interface VoucherRepository<K, V> {
    Voucher insert(Voucher voucher);

    Map<K, V> getVoucherList();
}
