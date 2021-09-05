package org.programmers.voucher.repository;

import org.programmers.voucher.model.VoucherInterface;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileMemoryVoucherRepository {
    Optional<VoucherInterface> findById(UUID voucherId);

    List<VoucherInterface> findAllVouchers();

    void save(VoucherInterface voucher);

    void deleteAll();
}
