package org.prgms.voucherProgram.voucher.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgms.voucherProgram.voucher.domain.Voucher;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    Voucher update(Voucher voucher);

    default List<Voucher> findAll() {
        throw new AssertionError();
    }

    default List<Voucher> findAll(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        throw new AssertionError();
    }

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByTypeAndDate(int type, LocalDateTime start, LocalDateTime end);

    void deleteById(UUID voucherId);

    void deleteAll();

    Voucher assignCustomer(Voucher voucher);

    List<Voucher> findByCustomerId(UUID customerId);

    List<Voucher> findByCustomerEmail(String customerEmail);

    List<Voucher> findNotAssign();

    List<Voucher> findAssigned();

    default List<Voucher> findByType(int type) {
        throw new AssertionError();
    }
}
