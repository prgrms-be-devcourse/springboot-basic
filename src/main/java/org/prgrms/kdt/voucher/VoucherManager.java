package org.prgrms.kdt.voucher;

import java.util.List;

public interface VoucherManager {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();
}
