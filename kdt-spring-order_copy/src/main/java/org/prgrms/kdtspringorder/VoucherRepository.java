package org.prgrms.kdtspringorder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
   Optional<Voucher> findById(UUID voucherId);

   void insert(Voucher voucher);
   public Map<UUID, Voucher> getAllVoucher();
}
