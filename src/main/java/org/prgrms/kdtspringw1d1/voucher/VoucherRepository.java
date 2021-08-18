package org.prgrms.kdtspringw1d1.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);
    Optional<Voucher> createFixedAmountVoucher();
    Optional<Voucher> createPercentDiscountVoucher();
    List<Voucher> findAll();
}
