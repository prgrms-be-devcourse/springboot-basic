package org.prgrms.prgrmsspring.repository.voucher;

import org.prgrms.prgrmsspring.entity.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    void delete(UUID voucherId);

    void clear();
}
