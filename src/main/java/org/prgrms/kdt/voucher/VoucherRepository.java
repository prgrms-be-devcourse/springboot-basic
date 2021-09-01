package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Repository;

import java.util.*;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
    void deleteAll();
    Map<UUID, Voucher> getStorage();
}
