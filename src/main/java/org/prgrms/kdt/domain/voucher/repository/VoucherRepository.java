package org.prgrms.kdt.domain.voucher.repository;

import org.prgrms.kdt.domain.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    UUID save(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByCustomerId(UUID customerId);

    List<Voucher> findAll();

    int updateById(Voucher voucher);

    void deleteById(UUID voucherId);

    void deleteAll();

    void deleteByCustomerId(UUID customerId);

}
