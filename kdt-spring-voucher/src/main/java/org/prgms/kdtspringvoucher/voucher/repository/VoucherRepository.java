package org.prgms.kdtspringvoucher.voucher.repository;

import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    Voucher update(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByCustomerId(UUID customerId);

    List<Voucher> findByParam(VoucherType voucherType, LocalDateTime from, LocalDateTime to);

    List<Voucher> findAll();

    void deleteAll();

    void deleteByCustomerId(UUID customerId);

    void deleteById(UUID voucherId);
}
