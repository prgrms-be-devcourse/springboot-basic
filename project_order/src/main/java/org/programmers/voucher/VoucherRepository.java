package org.programmers.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    int countAll();

    int countFixed();

    int countPercent();

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    void deleteAll();

}
