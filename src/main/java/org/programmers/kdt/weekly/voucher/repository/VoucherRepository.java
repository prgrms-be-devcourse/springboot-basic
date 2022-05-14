package org.programmers.kdt.weekly.voucher.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;

public interface VoucherRepository {

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    void deleteById(UUID voucherId);

    void deleteAll();

    List<Voucher> findByType(VoucherType voucherType);

    List<Voucher> findByCreatedAt(LocalDate begin, LocalDate end);
}