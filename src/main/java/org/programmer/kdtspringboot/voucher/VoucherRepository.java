package org.programmer.kdtspringboot.voucher;

import org.programmer.kdtspringboot.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    Voucher update(Voucher voucher);
    List<Voucher> findAll();
    Optional<Voucher> findById(UUID voucherId);


}
