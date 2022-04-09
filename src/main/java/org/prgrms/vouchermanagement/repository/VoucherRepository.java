package org.prgrms.vouchermanagement.repository;

import org.prgrms.vouchermanagement.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAll();

    Voucher save(Voucher voucher);
}
