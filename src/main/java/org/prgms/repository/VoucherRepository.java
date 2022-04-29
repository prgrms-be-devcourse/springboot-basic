package org.prgms.repository;

import org.prgms.domain.Voucher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    void save(Voucher voucher);

    List<Voucher> findAll();

    default List<Voucher> findByType(String voucherType) {
        return new ArrayList<>();
    }

    default List<Voucher> findByCreatedAt(LocalDate begin, LocalDate end) {
        return new ArrayList<>();
    }

    Optional<Voucher> findById(UUID voucherId);

    void deleteAll();

    default int deleteById(UUID voucherId) {
        return 0;
    }

}
