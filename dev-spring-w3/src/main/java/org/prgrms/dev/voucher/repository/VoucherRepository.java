package org.prgrms.dev.voucher.repository;

import org.prgrms.dev.voucher.domain.Voucher;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher create(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();
}
