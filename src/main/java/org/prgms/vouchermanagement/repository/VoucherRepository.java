package org.prgms.vouchermanagement.repository;

import org.prgms.vouchermanagement.voucher.Voucher;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);
    void saveVoucher(UUID voucherId, Voucher voucher);
    Optional<Map<UUID, Voucher>> getVoucherList();
}
