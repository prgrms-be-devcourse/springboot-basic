package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

  Optional<Voucher> findById(UUID voucherId);

  Voucher insert(Voucher voucher);

    Collection<Voucher> findAll();
}
