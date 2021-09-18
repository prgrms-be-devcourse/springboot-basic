package org.prgrms.devcourse.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    void deleteAll();
}
