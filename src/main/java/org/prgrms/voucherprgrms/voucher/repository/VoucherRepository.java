package org.prgrms.voucherprgrms.voucher.repository;

import org.prgrms.voucherprgrms.voucher.model.Voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    List<Voucher> findAll();

    List<Voucher> findByCreated(LocalDateTime start, LocalDateTime end);
    List<Voucher> findByVoucherType(String DTYPE);

    void deleteById(UUID voucherId);

    void deleteAll();
}
