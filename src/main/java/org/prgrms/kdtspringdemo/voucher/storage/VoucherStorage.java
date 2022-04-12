package org.prgrms.kdtspringdemo.voucher.storage;

import org.prgrms.kdtspringdemo.voucher.voucherdetail.Voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherStorage {
    public Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    Map<UUID, Voucher> getStorage();


}
