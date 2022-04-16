package org.voucherProject.voucherProject.voucher.repository;

import org.voucherProject.voucherProject.voucher.entity.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    void deleteAll();
}
