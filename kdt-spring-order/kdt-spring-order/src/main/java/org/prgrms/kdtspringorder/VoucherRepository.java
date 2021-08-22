package org.prgrms.kdtspringorder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
   Optional<Voucher> findById(UUID voucherId);

   void insert(Optional<Voucher> voucher);
   List<Voucher> getAllVoucher();
}
