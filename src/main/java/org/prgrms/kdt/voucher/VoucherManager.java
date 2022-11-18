package org.prgrms.kdt.voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherManager {
    void save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(long id);
}
