package org.prgrms.kdt.voucher;

import java.util.List;

public interface VoucherManager {
    void save(Voucher voucher);

    List<Voucher> findAll();
}
