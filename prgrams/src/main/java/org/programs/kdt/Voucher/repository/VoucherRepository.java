package org.programs.kdt.Voucher.repository;

import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.domain.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    List<Voucher> findAll();
    Voucher insert(Voucher voucher);
    Optional<Voucher> findById(UUID voucherId);
    Voucher update(Voucher voucher);
    void deleteAll();
    void delete(UUID uuid);
    List<Voucher> findByType(VoucherType voucherType);
    boolean existId(UUID customerId);
}
