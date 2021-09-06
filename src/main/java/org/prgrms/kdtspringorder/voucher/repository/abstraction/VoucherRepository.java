package org.prgrms.kdtspringorder.voucher.repository.abstraction;

import org.prgrms.kdtspringorder.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> getVouchers();

    Voucher saveVoucher(Voucher voucher);
}
