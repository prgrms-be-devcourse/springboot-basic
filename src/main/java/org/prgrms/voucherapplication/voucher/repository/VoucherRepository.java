package org.prgrms.voucherapplication.voucher.repository;

import org.prgrms.voucherapplication.voucher.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    int deleteAll();

    Optional<Voucher> findById(UUID voucherId);

    void deleteById(UUID voucherId);
}
