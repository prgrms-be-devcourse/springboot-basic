
package org.promgrammers.springbootbasic.domain.voucher.repository;

import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    void assignVoucherToCustomer(UUID customerId, UUID voucherId);

    void removeVoucherFromCustomer(UUID customerId, UUID voucherId);

    List<Voucher> findAllByCustomerId(UUID customerId);

    Voucher update(Voucher voucher);

    void deleteAll();

    void deleteById(UUID voucherId);
}
