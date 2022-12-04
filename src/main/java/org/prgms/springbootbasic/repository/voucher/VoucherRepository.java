package org.prgms.springbootbasic.repository.voucher;


import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findVouchersByCustomerId(UUID customerId);
    Voucher insert(Voucher voucher);
    UUID deleteById(UUID voucherId);
    UUID deleteByCustomerId(UUID customerId);

    Voucher updateByCustomerId(Voucher voucher);

    UUID updateByCustomerId(UUID customerId, UUID voucherID);
}
