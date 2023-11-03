package org.programmers.springorder.repository.voucher;

import org.programmers.springorder.model.voucher.Voucher;
import org.programmers.springorder.model.voucher.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByType(VoucherType voucherType);

    Voucher update(Voucher voucher);

    void deleteAll();

    void deleteById(UUID voucherId);

    void assignVoucherToCustomer(UUID customerId, UUID voucherId);

    List<Voucher> findByCustomerId(UUID customerId);
}
