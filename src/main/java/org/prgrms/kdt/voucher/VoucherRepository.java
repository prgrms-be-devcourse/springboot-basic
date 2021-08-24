package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
    Map<UUID, Voucher> getStorage();
}
