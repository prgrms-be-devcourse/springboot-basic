package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> find();

    Voucher insert(Voucher voucher);
}
