package org.prgrms.kdtspringdemo.domain.voucher.repository;

import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    int count();

    List<Voucher> findAll();

    Optional<Voucher> findByCustomerId(UUID customerId);

    Optional<Voucher> findById(UUID voucherId);

    void deleteAll();

}
