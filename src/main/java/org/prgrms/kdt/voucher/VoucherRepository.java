package org.prgrms.kdt.voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    List<Voucher> voucherList = new ArrayList<>();

    Optional<Voucher> findById(UUID voucherId);
    void insert(Voucher voucher);
}
