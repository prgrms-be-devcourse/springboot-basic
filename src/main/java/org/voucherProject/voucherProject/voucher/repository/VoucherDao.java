package org.voucherProject.voucherProject.voucher.repository;

import org.voucherProject.voucherProject.voucher.entity.Voucher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherDao {

    Voucher save(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    List<Voucher> findByCustomerId(UUID customerId);

    Voucher update(Voucher voucher);

    void deleteAll();

    void deleteOneByCustomerId(UUID customerId, UUID voucherId);
}
