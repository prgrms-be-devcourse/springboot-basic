package org.programmers.devcourse.voucher.engine.voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

  UUID insert(Voucher voucher);

  Optional<Voucher> getVoucher(UUID voucherId);

  Map<UUID, Voucher> getAllVouchers();
}
