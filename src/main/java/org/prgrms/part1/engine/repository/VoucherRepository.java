package org.prgrms.part1.engine.repository;

import org.prgrms.part1.engine.domain.Customer;
import org.prgrms.part1.engine.domain.Voucher;
import org.prgrms.part1.engine.enumtype.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    List<Voucher> findAll();

    int count();

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByType(VoucherType voucherType);

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    void deleteAll();
}
