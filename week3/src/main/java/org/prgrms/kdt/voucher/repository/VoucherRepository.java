package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.model.Voucher;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    int insert(Voucher voucher);
}
