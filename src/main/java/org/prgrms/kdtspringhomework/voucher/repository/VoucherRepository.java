package org.prgrms.kdtspringhomework.voucher.repository;

import org.prgrms.kdtspringhomework.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    Voucher add(Voucher voucher);
}
