package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.Voucher;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Map<UUID, Voucher> findByAllVoucher();

    Voucher insert(Voucher voucher) throws IOException;
}
