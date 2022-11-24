package org.prgrms.java.repository.voucher;

import org.prgrms.java.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByCustomer(UUID customerId);

    List<Voucher> findAll();

    Voucher update(Voucher voucher);

    void delete(UUID voucherId);

    long deleteAll();
}
