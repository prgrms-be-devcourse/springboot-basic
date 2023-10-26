package org.programmers.springorder.repository.voucher;

import org.programmers.springorder.model.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    UUID save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);
}
