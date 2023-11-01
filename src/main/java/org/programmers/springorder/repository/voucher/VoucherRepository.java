package org.programmers.springorder.repository.voucher;

import org.programmers.springorder.model.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    Voucher update(Voucher voucher);

    void deleteAll();

    void deleteById(UUID voucherId);

    void assignVoucherToCustomer(UUID customerId, UUID voucherId);

    List<Voucher> findByCustomerId(UUID customerId);
}
