package org.prgrms.kdtspringorder.voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
   Optional<Voucher> findById(UUID voucherId);
   Voucher insert(Voucher voucher);

//   void insert(Optional<Voucher> voucher);
//   List<Voucher> getAllVoucher();
}
