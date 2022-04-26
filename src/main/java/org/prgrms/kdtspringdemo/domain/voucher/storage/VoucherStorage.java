package org.prgrms.kdtspringdemo.domain.voucher.storage;

import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherStorage {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    Map<UUID, Voucher> getStorage();

}
