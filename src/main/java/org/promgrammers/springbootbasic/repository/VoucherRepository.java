package org.promgrammers.springbootbasic.repository;

import org.promgrammers.springbootbasic.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    void deleteAll();
}
