package org.prgrms.assignment.voucher.repository;

import org.prgrms.assignment.voucher.model.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoucherRepository {
    Optional<Voucher> findByID(UUID voucherId);
    Voucher insert(Voucher voucher);
    List<Voucher> findAll();
}
