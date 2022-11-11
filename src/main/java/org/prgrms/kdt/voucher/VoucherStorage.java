package org.prgrms.kdt.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherStorage {

    void save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);
}
