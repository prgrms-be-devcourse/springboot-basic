package org.programmer.kdtspringboot.voucher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    Voucher update(Voucher voucher);
    List<Voucher> findAll();
    Optional<Voucher> findById(UUID voucherId);
    void deleteAll();
    void deleteById(UUID voucherId);
    List<Voucher> findByType(String type);
    List<Voucher> findByDate(LocalDate createdAt);
}
