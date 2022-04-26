package org.prgms.voucherProgram.domain.voucher.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgms.voucherProgram.domain.voucher.domain.Voucher;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    Voucher update(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByTypeAndDate(int type, LocalDateTime start, LocalDateTime end);

    void deleteById(UUID voucherId);

    void deleteAll();

    Voucher assignCustomer(Voucher voucher);

    List<Voucher> findByCustomerId(UUID customerId);

    List<Voucher> findByCustomerEmail(String customerEmail);
}
