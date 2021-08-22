package org.prgrms.kdt.voucher;

import java.util.*;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Voucher insert(Voucher voucher);
    Map<UUID, Voucher> getStorage();
}
